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
@Table(name="ITEM_GRUPO_ATENDIMENTO")
public class ItemGrupoAtendimento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @EqualsAndHashCode.Include
	@Column(name="ID_ITEM_GRUPO_ATENDIMENTO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ITEM_GRUPO_ATENDIMENTO")
	@SequenceGenerator(name="SEQ_ITEM_GRUPO_ATENDIMENTO", sequenceName="SEQ_ITEM_GRUPO_ATENDIMENTO", allocationSize=1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_GRUPO_ATENDIMENTO", referencedColumnName = "ID_GRUPO_ATENDIMENTO", nullable = false)
	private GrupoAtendimento grupoAtendimento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SERVICO", referencedColumnName = "ID_SERVICO", nullable = false)
	private Servico servico;

}
