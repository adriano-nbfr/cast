package br.mp.mpf.cast.exception;

/**
 * Exception usada quando o erro gerado vier dos módulos de acesso ao Webservice do Sistema Único
 * e possuir mensagem sensível que não deva ser encaminhada ao frontend.
 */
public class RuntimeExceptionUnico extends RuntimeException {

    public RuntimeExceptionUnico(String mensagem) {
        super(mensagem);
    }

    public RuntimeExceptionUnico(Throwable t) {
        super(t);
    }
}
