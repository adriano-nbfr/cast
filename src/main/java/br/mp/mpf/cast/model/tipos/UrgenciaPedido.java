package br.mp.mpf.cast.model.tipos;

import com.fasterxml.jackson.annotation.JsonValue;

/** Um Enum que representa internamente a avaliação de um pedido. */
public enum UrgenciaPedido {
	MUITO_BAIXA(0, "Muito baixa"),
    BAIXA(1, "Baixa"),
    MEDIA(2, "Média"),
    ALTA(3, "Alta"),
    MUITO_ALTA(4, "Muito alta");

	private Integer key; //valor no banco de dados
	private String descricao; //valor usado no toString

	/**
	 * Construtor que vai aceitar a passagem do valor, nomeado "key"
	 * @param key - Valor, em parênteses, na definição do elemento da enum
	 */
	private UrgenciaPedido(Integer key, String descricao) {
		this.key = key;
        this.descricao = descricao;
	}

    @JsonValue
    public Integer getValor() {
       return this.ordinal();
    }

	/**
	 * Sobrescreve toString() para retornar o valor do banco de dados (key). Podendo assim ser usado para
	 * correlacionar o name(rótulo) ao value(valor) da enum.
	 * Originalmente o Java retorna o name quando não há sobrescrição do método
	 */
	@Override
	public String toString() {
		return this.descricao;
	}

	/**
	 * Retorna a instância da enum que possui o valor passado como parâmetro
	 * @param key - O valor a ser buscado
	 * @return - A instância correspondente, ou NULL
	 */
	public static UrgenciaPedido getByValor(Integer key) {
	     for (UrgenciaPedido v : values())
	    	 if (v.key == key)
	    		 return v;

	     return null;
	}

}