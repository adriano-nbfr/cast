package br.mp.mpf.cast.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
@Table(name="ARQUIVO_ANDAMENTO")
public class ArquivoAndamento implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int LIMITE_NOME_ARQUIVO = 100;

    @Id
    @EqualsAndHashCode.Include
	@Column(name="ID_ARQUIVO_ANDAMENTO", nullable=false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ARQUIVO_ANDAMENTO")
	@SequenceGenerator(name="SEQ_ARQUIVO_ANDAMENTO", sequenceName="SEQ_ARQUIVO_ANDAMENTO", allocationSize=1)
	private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ANDAMENTO_PEDIDO", referencedColumnName = "ID_ANDAMENTO_PEDIDO", nullable = false)
	private AndamentoPedido andamentoPedido;

    @Column(name="NM_ARQUIVO", length=LIMITE_NOME_ARQUIVO, nullable=false)
	private String nomeArquivo;

   	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="AQ_CONTEUDO_ARQUIVO")
	private byte[] arquivo;

    @Column(name="DS_TIPO_CONTEUDO", length=50)
	private String contentType;

}
