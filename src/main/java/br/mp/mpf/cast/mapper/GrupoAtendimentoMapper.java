package br.mp.mpf.cast.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import br.mp.mpf.cast.dto.GrupoAtendimentoDto;
import br.mp.mpf.cast.dto.GrupoAtendimentoMinimoDto;
import br.mp.mpf.cast.model.GrupoAtendimento;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GrupoAtendimentoMapper {

	GrupoAtendimento paraModelo(GrupoAtendimentoDto grupoAtendimentoDto);
	GrupoAtendimentoDto paraDto(GrupoAtendimento grupoAtendimento);
	GrupoAtendimentoMinimoDto paraDtoMinimo(GrupoAtendimento grupoAtendimento);

	List<GrupoAtendimento> paraListaModelo(List<GrupoAtendimentoDto> gruposAtendimentoDto);
	List<GrupoAtendimentoDto> paraListaDto(List<GrupoAtendimento> gruposAtendimento);
	List<GrupoAtendimentoMinimoDto> paraListaDtoMinimo(List<GrupoAtendimento> gruposAtendimento);
}
