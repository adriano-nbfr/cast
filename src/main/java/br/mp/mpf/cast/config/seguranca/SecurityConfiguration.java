package br.mp.mpf.cast.config.seguranca;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

import br.mp.mpf.autoriza.modelo.AuthorizeRequestPersonalizado;
import br.mp.mpf.cast.security.AuthoritiesConstants;


@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfiguration  {

	public static final String APP_ADMIN_USER_NAME 	= "sistema.app.admin.username";

	@Autowired private Environment env;


    /* Utilizado pelo módulo Oauth para barrar acesso às URLs sem a devida permissão */
    @Bean
    AuthorizeRequestPersonalizado<HttpSecurity> appAuthorizeRequestsCustomizer() {
        return (customizer) -> {
			customizer
                .antMatchers("/api/catalogo/**", "/api/pedidos/**").authenticated()
                .antMatchers("/api/manutencao/**").hasAnyAuthority(AuthoritiesConstants.PAPEL_GERENTE)
                .antMatchers("/api/public/**", "/frontend/**").permitAll() // endpoints públicos da API e arquivos estáticos
                .antMatchers("/", "/home", "/publico/**").permitAll(); // rotas específicas do frontend que não requerem autenticação
		};
    }


	/* Conteúdo estático do frontend (js, estilos, imagens, fontes etc.) servido sem filtro de segurança.
     * Apenas recursos que realmente não necessitem de proteção podem ser listados aqui.
     * Obs: O Spring Security exibe mensagens de warning no log, desaconselhando em favor de usar com .permitAll() acima. */
	@Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) ->
        	web.ignoring()
				.antMatchers("/error");
    }


	@Bean
	Set<String> adminList() {
		Set<String> admins = new HashSet<String>();
		String adminUserName = env.getProperty(APP_ADMIN_USER_NAME);

		admins.add("admin");

		if (adminUserName != null && !adminUserName.isEmpty())
			admins.add(adminUserName);

		return admins;
	}


	@Bean
	SessionAuthenticationStrategy sessionStrategy() {
		SessionFixationProtectionStrategy sessionStrategy = new SessionFixationProtectionStrategy();
		sessionStrategy.setMigrateSessionAttributes(false);
		return sessionStrategy;
	}

}
