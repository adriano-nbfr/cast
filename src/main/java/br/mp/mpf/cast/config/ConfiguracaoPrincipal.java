package br.mp.mpf.cast.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;


@Configuration
public class ConfiguracaoPrincipal {

	@Bean   // Para uso nos endpoints de monitoramento memória JVM e espaço disco - ServicoMonitoramento
	RestTemplate restTemplate() {
	    return new RestTemplate();
	}


	@Bean(name = "contextoRequisicao")
	@RequestScope // Importante que este bean tenha escopo apenas de REQUEST
	ContextoRequisicao contextoRequisicao() {
		return new ContextoRequisicao();
	}

}
