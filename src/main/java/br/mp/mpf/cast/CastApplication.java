package br.mp.mpf.cast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

// Access Manager
@ComponentScan("br.mp.mpf")
@EntityScan("br.mp.mpf")
@EnableJpaRepositories(basePackages = "br.mp.mpf.cast")
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
// FIM - Access Manager
@EnableScheduling
public class CastApplication extends SpringBootServletInitializer {

	private static Class<CastApplication> applicationClass = CastApplication.class;

	// Informa ao deploy war quais as nossas classes responsáveis pela configuração do nosso projeto.
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	public static void main(String[] args) {
		SpringApplication.run(CastApplication.class, args);
	}

}
