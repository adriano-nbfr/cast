package br.mp.mpf.cast.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.mp.mpf.cast.model.tipos.AvaliacaoPedido;
import br.mp.mpf.cast.model.tipos.StatusPedido;
import br.mp.mpf.cast.model.tipos.UrgenciaPedido;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
@Table(name="PEDIDO")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @EqualsAndHashCode.Include
	@Column(name="ID_PEDIDO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_PEDIDO")
	@SequenceGenerator(name="SEQ_PEDIDO", sequenceName="SEQ_PEDIDO", allocationSize=1)
	private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SERVICO", referencedColumnName = "ID_SERVICO", nullable = false)
	private Servico servico;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_GRUPO_ATENDIMENTO", referencedColumnName = "ID_GRUPO_ATENDIMENTO", nullable = true)
	private GrupoAtendimento grupoAtendimento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO_ABERTURA", referencedColumnName = "ID_USUARIO", nullable = false)
	private Usuario usuarioAbertura;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO_SOLICITANTE", referencedColumnName = "ID_USUARIO", nullable = false)
	private Usuario usuarioSolicitante;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO_ATENDENTE", referencedColumnName = "ID_USUARIO", nullable = true)
	private Usuario usuarioAtendente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO_FECHAMENTO", referencedColumnName = "ID_USUARIO", nullable = true)
	private Usuario usuarioFechamento;

    @Column(name="DS_TITULO", length=200, nullable = false)
	private String titulo;

	@Column(name="TX_DESCRICAO", length=4000, nullable = false)
	private String descricao;

	@Column(name="ST_URGENCIA", nullable = false)
	private UrgenciaPedido urgencia;

    @Enumerated(EnumType.STRING)
    @Column(name="ST_STATUS", length=1, nullable = false)
    StatusPedido status;

	@Column(name = "DT_ABERTURA", nullable = false)
	private ZonedDateTime dataAbertura;

	@Column(name = "DT_FECHAMENTO", nullable = true)
	private ZonedDateTime dataFechamento;

    @Column(name="ST_AVALIACAO", nullable = true)
	private AvaliacaoPedido avaliacao;

    @Column(name="TX_FECHAMENTO", length=1000, nullable = true)
	private String textoFechamento;
}
