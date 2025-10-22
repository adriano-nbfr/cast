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
public class PedidoListagemDto extends EntidadeGenericaDto {

	private Long id;
    private ServicoMinimoDto servico;
    private UsuarioMinimoDto usuarioSolicitante;
    private UsuarioMinimoDto usuarioAtendente;
    private String titulo;
    private String descricao;
    private UrgenciaPedido urgencia;
    private StatusPedido status;
    private ZonedDateTime dataAbertura;
    private ZonedDateTime dataAtualizacao;

    // Construtor adicional, desnormalizado para permitir a criação diretamente da query jpql
    public PedidoListagemDto(
        Long id,
        Long idServico,
        String nomeServico,
        Long idUsuarioSolicitante,
        String nomeUsuarioSolicitante,
        Long idUsuarioAtendente,
        String nomeUsuarioAtendente,
        String titulo,
        String descricao,
        UrgenciaPedido urgencia,
        StatusPedido status,
        ZonedDateTime dataAbertura,
        ZonedDateTime dataAtualizacao) {

        this.id = id;
        this.servico = new ServicoMinimoDto(idServico, nomeServico);
        this.usuarioSolicitante = new UsuarioMinimoDto(idUsuarioSolicitante, nomeUsuarioSolicitante);

        if (idUsuarioAtendente != null)
            this.usuarioAtendente = new UsuarioMinimoDto(idUsuarioAtendente, nomeUsuarioAtendente);

        this.titulo = titulo;
        this.descricao = descricao;
        this.urgencia = urgencia;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.dataAtualizacao = dataAtualizacao;
    }

}
