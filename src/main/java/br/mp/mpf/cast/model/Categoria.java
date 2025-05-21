package br.mp.mpf.cast.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="CATEGORIA")
public class Categoria  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @EqualsAndHashCode.Include
	@Column(name="ID_CATEGORIA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CATEGORIA")
	@SequenceGenerator(name="SEQ_CATEGORIA", sequenceName="SEQ_CATEGORIA", allocationSize=1)
	private Long id;

	@Column(name="NM_CATEGORIA", length=100, nullable=false)
	private String nome;

    @Column(name="DS_CATEGORIA", length=200)
	private String descricao;

	/**
	 * Categoria está ativa?
	 * 0: Não
	 * 1: Sim (Default)
	 */
	@Column(name="ST_ATIVO", nullable=false)
	private boolean ativo = true;
}
