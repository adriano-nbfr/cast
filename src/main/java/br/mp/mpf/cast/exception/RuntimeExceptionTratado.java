package br.mp.mpf.cast.exception;

/**
 * Exception usada quando o erro gerado já vier tratado e puder ser encaminhado diretamente ao Frontend
 */
public class RuntimeExceptionTratado extends RuntimeException {

    public RuntimeExceptionTratado(String mensagem) {
        super(mensagem);
    }

    public RuntimeExceptionTratado(Throwable t) {
        super(t);
    }
}
