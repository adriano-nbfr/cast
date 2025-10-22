package br.mp.mpf.cast.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import br.mp.mpf.cast.dto.ServicoDto;
import br.mp.mpf.cast.dto.ServicoListagemDto;
import br.mp.mpf.cast.dto.ServicoMinimoDto;
import br.mp.mpf.cast.model.Servico;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServicoMapper {

	Servico paraModelo(ServicoDto servicoDto);
	ServicoDto paraDto(Servico servico);
	ServicoListagemDto paraDtoListagem(Servico servico);
	ServicoMinimoDto paraDtoMinimo(Servico servico);

	List<Servico> paraListaModelo(List<ServicoDto> servicosDto);
	List<ServicoDto> paraListaDto(List<Servico> servicos);
	List<ServicoListagemDto> paraListaDtoListagem(List<Servico> servicos);
	List<ServicoMinimoDto> paraListaDtoMinimo(List<Servico> servicos);

}
