package br.mp.mpf.cast.model;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name="USUARIO_GRUPO_ATENDIMENTO")
public class UsuarioGrupoAtendimento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @EqualsAndHashCode.Include
	@Column(name="ID_USUARIO_GRUPO_ATENDIMENTO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_USUARIO_GRUPO_ATENDIMENTO")
	@SequenceGenerator(name="SEQ_USUARIO_GRUPO_ATENDIMENTO", sequenceName="SEQ_USUARIO_GRUPO_ATENDIMENTO", allocationSize=1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_GRUPO_ATENDIMENTO", referencedColumnName = "ID_GRUPO_ATENDIMENTO", nullable = false)
	private GrupoAtendimento grupoAtendimento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", nullable = false)
	private Usuario usuario;

	@Column(name = "DT_INICIO", nullable = false)
	private LocalDate dataInicio; // Caso a hora seja importante, deve se usar ZonedDateTime

	@Column(name = "DT_FIM", nullable = true)
	private LocalDate dataFim; // Caso a hora seja importante, deve se usar ZonedDateTime

}
