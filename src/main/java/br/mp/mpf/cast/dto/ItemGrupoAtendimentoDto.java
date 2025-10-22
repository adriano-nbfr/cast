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
public class ItemGrupoAtendimentoDto extends EntidadeGenericaDto {

	private Long id;
    private GrupoAtendimentoMinimoDto grupoAtendimento;
    private ServicoMinimoDto servico;


    // Construtor adicional, desnormalizado para permitir a criação diretamente da query jpql
    public ItemGrupoAtendimentoDto(
        Long id,
        Long idGrupoAtendimento,
        String nomeGrupoAtendimento,
        Long idServico,
        String nomeServico) {

        this.id = id;
        this.grupoAtendimento = new GrupoAtendimentoMinimoDto(idGrupoAtendimento, nomeGrupoAtendimento);
        this.servico = new ServicoMinimoDto(idServico, nomeServico);
    }

}
