package br.mp.mpf.cast.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mp.mpf.cast.security.AuthoritiesConstants;
import br.mp.mpf.seguranca.UsuarioBean;

/**
 * Serviço responsável pelos aspectos de acesso/segurança da aplicação
 */
@Service
public class SegurancaService {

	@Autowired private UsuarioBean usuarioBean;


	// Verifica se existe usuarioLogado
	private boolean existeUsuarioLogado() {
		try {
			if (usuarioBean == null || usuarioBean.getUsuario() == null)
				return false;
		}
		// Levantará a exceção caso o UsuarioBean não esteja acessível.
		// Caso da thread executar sem RequestContext (session)
		catch (Exception e) {
			return false;
		}

		return true;
	}


	public boolean isAdmin() {
		if (!existeUsuarioLogado())
			return false;

		return usuarioBean.hasAuthority(AuthoritiesConstants.PAPEL_ADMIN);
	}


	/**
	 * Verifica se o usuário atual tem o papel de gerente
	 */
	public boolean isGerente() {
		if (!existeUsuarioLogado())
			return false;

		return usuarioBean.hasAuthority(AuthoritiesConstants.PAPEL_GERENTE);
	}


	public Collection<String> getPapeis() {
		return this.usuarioBean.getPapeis();
	}

}
