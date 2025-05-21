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
@Table(name="USUARIO")
public class Usuario  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @EqualsAndHashCode.Include
	@Column(name="ID_USUARIO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_USUARIO")
	@SequenceGenerator(name="SEQ_USUARIO", sequenceName="SEQ_USUARIO", allocationSize=1)
	private Long id;

	@Column(name="NM_USUARIO", length=100, nullable=false)
	private String nome;

    @Column(name="EMAIL_USUARIO", length=100, nullable=false)
	private String email;

	/**
	 * USUARIO está ativo?
	 * 0: Não
	 * 1: Sim (Default)
	 */
	@Column(name="ST_ATIVO", nullable=false)
	private boolean ativo = true;

}
