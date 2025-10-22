package br.mp.mpf.cast.controller;

import java.sql.SQLException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.mp.mpf.cast.dto.comum.ErroDto;
import br.mp.mpf.cast.exception.RuntimeExceptionTratado;
import br.mp.mpf.cast.exception.RuntimeExceptionUnico;
import br.mp.mpf.cast.service.SegurancaService;


/** Um controller que trata as exceções em tempo de execução, fazendo com que o retorno seja um Dto com o erro tratado. */
@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler {

	@Autowired private SegurancaService segurancaService;

	/**
	 * Trata exceções de banco de dados. Normalmente as do Spring e Hibernate não virão aqui
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(SQLException.class)
	@ResponseBody
	public ErroDto tratarSQLException(SQLException e) {

		String mensagemErro = "Erro no banco de dados.";
		boolean embutirCausa = false; // Pode vir de uma verificação se o usuário é admin
		return new ErroDto(mensagemErro, embutirCausa ? ExceptionUtils.getRootCauseMessage(e) : null);
	}


	/**
	 * Normalmente as exceções vão cair neste método, inclusive as do Spring e Hibernate
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public ErroDto tratarRuntimeException(RuntimeException e) {

		String mensagemErro;
		Throwable cause = e.getCause();
		Throwable rootCause = ExceptionUtils.getRootCause(e);

		// trata null pointer exception
		if (e.getClass().equals(NullPointerException.class))
			mensagemErro = "Ocorreu ume erro ao tentar acessar um atributo de um objeto nulo.";
		// Qualquer erro que derive de ConstraintViolationException. Mensagem original não deve ser enviada ao frontend
		else if ( cause != null && ConstraintViolationException.class.isAssignableFrom(cause.getClass()))
			mensagemErro = "Ocorreu um erro devido a uma restrição de integridade no banco de dados que impediu a operação.";
		// Qualquer erro que derive de HibernateException. Mensagem original não deve ser enviada ao frontend
		else if ( cause != null && HibernateException.class.isAssignableFrom(cause.getClass()))
			mensagemErro = "Ocorreu um erro no mapeamento de elementos do banco de dados.";
		// Qualquer erro que derive de SQLException. Mensagem original não deve ser enviada ao frontend
		else if (cause != null && SQLException.class.isAssignableFrom(cause.getClass()))
			mensagemErro = "Ocorreu um erro ao realizar uma consulta no banco de dados.";
		// Mensagens de runtime geradas pela rotina de webservice do Único. Mensagem original não deve ser enviada ao frontend
		else if (e.getClass().equals(RuntimeExceptionUnico.class))
			mensagemErro = "Ocorreu um erro no módulo de comunicação com o Sistema Único.";
		// Mensagens de runtime geradas pela aplicação. Pode enviar ao Frontend sem tratamento
		else if (e.getClass().equals(RuntimeExceptionTratado.class))
			return new ErroDto(e);
		// Demais mensagens de RuntimeException
		else
			mensagemErro = "Ocorreu um erro na execução da operação no servidor.";

		boolean embutirCausa = rootCause != null && segurancaService.isAdmin();
		return new ErroDto(mensagemErro, embutirCausa ? rootCause.getMessage() : null);
	}


	/**
	 * Tratamento genérico, caso não seja capturado pelos métodos anteriores
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ErroDto tratarExcecaoPadrao(Exception e) {

		Throwable rootCause = ExceptionUtils.getRootCause(e);
		boolean embutirCausa = rootCause != null && segurancaService.isAdmin();
		return new ErroDto("Ocorreu um erro interno.", embutirCausa ? rootCause.getMessage() : null);
	}

}
