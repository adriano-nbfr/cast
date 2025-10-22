package br.mp.mpf.cast.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import br.mp.mpf.cast.dto.CategoriaDto;
import br.mp.mpf.cast.dto.CategoriaListagemDto;
import br.mp.mpf.cast.dto.CategoriaMinimoDto;
import br.mp.mpf.cast.model.Categoria;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoriaMapper {

	Categoria paraModelo(CategoriaDto categoriaDto);
	CategoriaDto paraDto(Categoria categoria);
	CategoriaListagemDto paraDtoListagem(Categoria categoria);
	CategoriaMinimoDto paraDtoMinimo(Categoria categoria);

	List<Categoria> paraListaModelo(List<CategoriaDto> categoriasDto);
	List<CategoriaDto> paraListaDto(List<Categoria> categorias);
	List<CategoriaListagemDto> paraListaDtoListagem(List<Categoria> categorias);
	List<CategoriaMinimoDto> paraListaDtoMinimo(List<Categoria> categorias);
}
