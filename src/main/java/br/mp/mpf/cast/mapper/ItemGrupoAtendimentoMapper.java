package br.mp.mpf.cast.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import br.mp.mpf.cast.dto.ItemGrupoAtendimentoDto;
import br.mp.mpf.cast.model.ItemGrupoAtendimento;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemGrupoAtendimentoMapper {

	ItemGrupoAtendimento paraModelo(ItemGrupoAtendimentoDto itemGrupoAtendimentoDto);
	ItemGrupoAtendimentoDto paraDto(ItemGrupoAtendimento itemGrupoAtendimento);

	List<ItemGrupoAtendimento> paraListaModelo(List<ItemGrupoAtendimentoDto> itensGrupoAtendimentoDto);
	List<ItemGrupoAtendimentoDto> paraListaDto(List<ItemGrupoAtendimento> itensGrupoAtendimento);
}
