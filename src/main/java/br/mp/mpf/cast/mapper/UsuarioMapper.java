package br.mp.mpf.cast.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import br.mp.mpf.cast.dto.UsuarioDto;
import br.mp.mpf.cast.dto.UsuarioMinimoDto;
import br.mp.mpf.cast.model.Usuario;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

	Usuario paraModelo(UsuarioDto usuarioDto);
	UsuarioDto paraDto(Usuario usuario);
	UsuarioMinimoDto paraDtoMinimo(Usuario usuario);

	List<Usuario> paraListaModelo(List<UsuarioDto> usuariosDto);
	List<UsuarioDto> paraListaDto(List<Usuario> usuarios);
	List<UsuarioMinimoDto> paraListaDtoMinimo(List<Usuario> usuarios);
}
