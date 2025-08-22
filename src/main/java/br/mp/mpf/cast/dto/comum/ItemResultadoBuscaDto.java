package br.mp.mpf.cast.dto.comum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** Dto genérico para ser usado como item em uma lista de retorno para uma requisição de busca. */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemResultadoBuscaDto {

	Object valor;
	String rotulo;
}
