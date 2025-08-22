package br.mp.mpf.cast.dto.comum;

import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.Getter;

@Getter
public class ErroDto {

	private String message;
	private String cause;


	public ErroDto(String mensagem, String causa) {
		this.message = mensagem;
		this.cause = causa;
	}


	public ErroDto(String mensagem) {
		this.message = mensagem;
		this.cause = null;
	}


	public ErroDto(Exception e) {
		Throwable rootCause = ExceptionUtils.getRootCause(e);
		this.message = (rootCause != null ? rootCause.getMessage() : e.getMessage()).trim();
	}
}
