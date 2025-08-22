package br.mp.mpf.cast.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfiguration implements
	WebMvcConfigurer,
	ServletContextInitializer,
	WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

	private final Logger log = LoggerFactory.getLogger(WebConfiguration.class);

	@Autowired private Environment env;


	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		log.info("Web application configuration, using profiles: {}", Arrays.toString(env.getActiveProfiles()));
		log.info("Web application fully configured");
	}

	/**
	 * Set up Mime types.
	 */
	@Override
	public void customize(ConfigurableServletWebServerFactory container) {
		MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
		// IE issue, see https://github.com/jhipster/generator-jhipster/pull/711
		mappings.add("html", "text/html;charset=utf-8");
		// CloudFoundry issue, see
		// https://github.com/cloudfoundry/gorouter/issues/64
		mappings.add("json", "text/html;charset=utf-8");
		container.setMimeMappings(mappings);
	}


    /** Permite que urls fornecidas diretamente no navegador para rotas da aplicação não resultem em uma página de erro,
     * sendo direcionadas para a index para que sejam tratadas adequadamente pelo frontend */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
			.addResourceLocations("classpath:/static/", "classpath:/public/")
			.resourceChain(true)
			.addResolver(new PathResourceResolver() {
				@Override
				protected Resource getResource(String resourcePath, Resource location) throws IOException {
					Resource requestedResource = super.getResource(resourcePath, location);

					if (requestedResource != null)
						return requestedResource;

                    String path = (resourcePath.startsWith("/") ? "" : "/") + resourcePath;
                    boolean isApi = path.startsWith("/api/") || "/api".equals(path);
					boolean isRotaSpa = !isApi && !path.contains("."); // se não for Api ou arquivo estático

					// Requests para rotas internas da SPA serão levadas à index.
					if (isRotaSpa)
						return new ClassPathResource("/static/index.html");

					return null;
				}
			});
	}

}
