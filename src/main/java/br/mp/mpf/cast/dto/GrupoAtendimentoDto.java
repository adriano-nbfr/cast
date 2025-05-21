package br.mp.mpf.cast.dto;

import br.mp.mpf.cast.dto.comum.EntidadeGenericaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GrupoAtendimentoDto extends EntidadeGenericaDto {

	private Long id;
	private String nome;
	private boolean ativo;
    private CategoriaMinimoDto categoria;


    // Construtor adicional, desnormalizado para permitir a criação diretamente da query jpql
    public GrupoAtendimentoDto(
        Long id,
        String nome,
        boolean ativo,
        Long idCategoria,
        String nomeCategoria) {

        this.id = id;
        this.nome = nome;
        this.ativo = ativo;

        this.categoria = idCategoria != null ?
            new CategoriaMinimoDto(idCategoria, nomeCategoria) :
            null;
    }

}
