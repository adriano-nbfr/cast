package br.mp.mpf.cast.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import br.mp.mpf.cast.dto.PedidoDto;
import br.mp.mpf.cast.dto.PedidoListagemDto;
import br.mp.mpf.cast.model.Pedido;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PedidoMapper {

	Pedido paraModelo(PedidoDto pedidoDto);
	PedidoDto paraDto(Pedido pedido);
	PedidoListagemDto paraDtoListagem(Pedido pedido);

	List<Pedido> paraListaModelo(List<PedidoDto> pedidosDto);
	List<PedidoDto> paraListaDto(List<Pedido> pedidos);
    List<PedidoListagemDto> paraListaDtoListagem(List<Pedido> pedidos);

}