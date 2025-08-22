package br.mp.mpf.cast.model.tipos;

/** Um Enum que representa internamente os status Inativo e Ativo como 0 e 1, respectivamente. */
public enum StatusAtivo {
	INATIVO(0),
    ATIVO(1);

	private Integer key; //valor no banco de dados

	/**
	 * Construtor que vai aceitar a passagem do valor, nomeado "key"
	 * @param key - Valor, em parênteses, na definição do elemento da enum
	 */
	private StatusAtivo(Integer key) {
		this.key = key;
	}


	/**
	 * Sobrescreve toString() para retornar o valor do banco de dados (key). Podendo assim ser usado para
	 * correlacionar o name(rótulo) ao value(valor) da enum.
	 * Originalmente o Java retorna o name quando não há sobrescrição do método
	 */
	@Override
	public String toString() {
		return this.key.toString();
	}


	/**
	 * Retorna a instância da enum que possui o valor passado como parâmetro
	 * @param key - O valor a ser buscado
	 * @return - A instância correspondente, ou NULL
	 */
	public static StatusAtivo getByValor(Integer key) {
	     for (StatusAtivo v : values())
	    	 if (v.key == key)
	    		 return v;

	     return null;
	}

}
