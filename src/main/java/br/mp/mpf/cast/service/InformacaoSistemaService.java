package br.mp.mpf.cast.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import br.mp.mpf.cast.dto.comum.InformacaoSistemaDto;

/**
 * Serviço responsável por informações técnicas da instância do sistema em execução
 */
@Service
public class InformacaoSistemaService {

    @Autowired
    private BuildProperties buildProperties;

	@Autowired
	private Environment environment;


    public InformacaoSistemaDto obterInformacaoSistema() {
        LocalDateTime buildTime = LocalDateTime.ofInstant(
            buildProperties.getTime(),
            ZoneId.systemDefault());

        String dataSistema = buildTime != null ?
            buildTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")):
            "";

        String versaoMaven = environment.getProperty("app.version.maven", "");
        String versaoCI = environment.getProperty("app.version.ci", "");
        String versao = !versaoCI.isBlank() ? versaoCI : !versaoMaven.isBlank() ? versaoMaven : "";

        String ambiente = environment.getProperty("ambiente");

        String hostName;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostName = "";
        }

        return new InformacaoSistemaDto(
            valorTratado(dataSistema),
            valorTratado(versao),
            valorTratado(ambiente).toLowerCase(),
            valorTratado(hostName)
        );
    }


    private String valorTratado(String valor) {
        String valorTratado = Objects.toString(valor, "").trim();
        return valorTratado.isEmpty() ? "Indisponível" : valorTratado;
    }

}
