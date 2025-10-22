package br.mp.mpf.cast.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

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
@Table(name="ANDAMENTO_PEDIDO")
public class AndamentoPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    @Column(name="ID_ANDAMENTO_PEDIDO")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ANDAMENTO_PEDIDO")
    @SequenceGenerator(name="SEQ_ANDAMENTO_PEDIDO", sequenceName="SEQ_ANDAMENTO_PEDIDO", allocationSize=1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PEDIDO", referencedColumnName = "ID_PEDIDO", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @Column(name="TX_DESCRICAO", length=4000, nullable = false)
    private String descricao;

    @Column(name = "DT_REGISTRO", nullable = false)
    private ZonedDateTime dataRegistro;

}
