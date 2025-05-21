package br.mp.mpf.cast.model.tipos;

import com.fasterxml.jackson.annotation.JsonValue;

/** Um Enum que pode ser usado para guardar no BD os status de um pedido. */
public enum StatusPedido {
    C("Cadastrado"),
    A("Atribuído"),
    E("Em espera"),
    F("Fechado");

    private String descricao;

    private StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue()
    public String getNome() {
       return this.name();
    }

    @Override
    public String toString() {
        return this.descricao;
    }

}