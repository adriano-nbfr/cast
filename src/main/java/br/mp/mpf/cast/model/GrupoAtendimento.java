package br.mp.mpf.cast.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name="GRUPO_ATENDIMENTO")
public class GrupoAtendimento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @EqualsAndHashCode.Include
	@Column(name="ID_GRUPO_ATENDIMENTO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GRUPO_ATENDIMENTO")
	@SequenceGenerator(name="SEQ_GRUPO_ATENDIMENTO", sequenceName="SEQ_GRUPO_ATENDIMENTO", allocationSize=1)
	private Long id;

	@Column(name="NM_GRUPO_ATENDIMENTO", length=100, nullable=false)
	private String nome;

	/**
	 * Grupo está ativo?
	 * 0: Não
	 * 1: Sim (Default)
	 */
	@Column(name="ST_ATIVO", nullable=false)
	private boolean ativo = true;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CATEGORIA", referencedColumnName = "ID_CATEGORIA", nullable = true)
	private Categoria categoria;

}
