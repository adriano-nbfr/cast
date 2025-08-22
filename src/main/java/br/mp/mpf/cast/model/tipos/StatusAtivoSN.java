package br.mp.mpf.cast.model.tipos;

/** Um Enum que pode ser usado para guardar no BD os status Inativo e Ativo como "N" e "S", respectivamente. */
public enum StatusAtivoSN {
    S("Ativo"),
    N("Inativo");

    private String descricao;

    private StatusAtivoSN(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
