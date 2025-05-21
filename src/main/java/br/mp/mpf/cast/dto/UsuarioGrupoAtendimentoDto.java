package br.mp.mpf.cast.dto;

import java.time.LocalDate;

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
public class UsuarioGrupoAtendimentoDto extends EntidadeGenericaDto {

	private Long id;
    private GrupoAtendimentoMinimoDto grupoAtendimento;
    private UsuarioMinimoDto usuario;
    private LocalDate dataInicio;
    private LocalDate dataFim;


    // Construtor adicional, desnormalizado para permitir a criação diretamente da query jpql
    public UsuarioGrupoAtendimentoDto(
        Long id,
        Long idGrupoAtendimento,
        String nomeGrupoAtendimento,
        Long idUsuario,
        String nomeUsuario,
        LocalDate dataInicio,
        LocalDate dataFim) {

        this.id = id;
        this.grupoAtendimento = new GrupoAtendimentoMinimoDto(idGrupoAtendimento, nomeGrupoAtendimento);
        this.usuario = new UsuarioMinimoDto(idUsuario, nomeUsuario);
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

}
