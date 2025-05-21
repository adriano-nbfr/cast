package br.mp.mpf.cast.dto;

import java.time.ZonedDateTime;

import br.mp.mpf.cast.dto.comum.EntidadeGenericaDto;
import br.mp.mpf.cast.model.tipos.StatusPedido;
import br.mp.mpf.cast.model.tipos.UrgenciaPedido;
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
public class PedidoDto extends EntidadeGenericaDto {

	private Long id;
    private ServicoMinimoDto servico;
    private GrupoAtendimentoMinimoDto grupoAtendimento;
    private UsuarioMinimoDto usuarioAbertura;
    private UsuarioMinimoDto usuarioSolicitante;
    private UsuarioMinimoDto usuarioAtendente;
    private UsuarioMinimoDto usuarioFechamento;
    private String titulo;
    private String descricao;
    private UrgenciaPedido urgencia;
    private StatusPedido status;
    private ZonedDateTime dataAbertura;
    private ZonedDateTime dataFechamento;

}
