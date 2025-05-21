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
public class GrupoAtendimentoMinimoDto extends EntidadeGenericaDto {

    private Long id;
    private String nome;

}
