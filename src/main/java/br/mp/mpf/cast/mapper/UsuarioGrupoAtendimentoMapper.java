package br.mp.mpf.cast.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import br.mp.mpf.cast.dto.GrupoAtendimentoDto;
import br.mp.mpf.cast.dto.UsuarioGrupoAtendimentoDto;
import br.mp.mpf.cast.model.UsuarioGrupoAtendimento;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioGrupoAtendimentoMapper {

	UsuarioGrupoAtendimento paraModelo(UsuarioGrupoAtendimentoDto usuarioGrupoAtendimentoDto);
	UsuarioGrupoAtendimentoDto paraDto(UsuarioGrupoAtendimento usuarioGrupoAtendimento);

	List<UsuarioGrupoAtendimento> paraListaModelo(List<GrupoAtendimentoDto> usuariosGrupoAtendimentoDto);
	List<UsuarioGrupoAtendimentoDto> paraListaDto(List<UsuarioGrupoAtendimento> usuariosGrupoAtendimento);

}
