package br.mp.mpf.cast.dto;

import br.mp.mpf.cast.model.tipos.AvaliacaoPedido;
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
public class PedidoFechamentoDto {
    private AvaliacaoPedido avaliacao;
    private String textoFechamento;
}
