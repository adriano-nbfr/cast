package br.mp.mpf.cast.config;

import java.util.HashMap;

/** Permite armazenar parâmetros diversos durante o ciclo de vida de uma requisição,
 * para serem usados em diferentes partes da aplicação
 */
public class ContextoRequisicao {

    private HashMap<String, Object> parametros = new HashMap<String, Object>();


    public void configurarParametro(String parametro, Object valor) {

        this.parametros.put(parametro, valor);
    }

    public Object obterParametro(String parametro) {

        return this.parametros.get(parametro);
    }

    public void limparParametro(String parametro) {

        if (this.parametros.containsKey(parametro))
            this.parametros.remove(parametro);
    }
}
