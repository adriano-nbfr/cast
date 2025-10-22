package br.mp.mpf.cast.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import br.mp.mpf.cast.dto.AndamentoPedidoDto;
import br.mp.mpf.cast.model.AndamentoPedido;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AndamentoPedidoMapper {

	AndamentoPedido paraModelo(AndamentoPedidoDto andamentoPedidoDto);
	AndamentoPedidoDto paraDto(AndamentoPedido andamentoPedido);

	List<AndamentoPedido> paraListaModelo(List<AndamentoPedidoDto> andamentosPedidoDto);
	List<AndamentoPedidoDto> paraListaDto(List<AndamentoPedido> andamentosPedido);

}