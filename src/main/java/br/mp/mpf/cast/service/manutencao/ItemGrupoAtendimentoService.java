package br.mp.mpf.cast.service.manutencao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.mp.mpf.cast.dto.GrupoAtendimentoMinimoDto;
import br.mp.mpf.cast.dto.ItemGrupoAtendimentoDto;
import br.mp.mpf.cast.model.ItemGrupoAtendimento;
import br.mp.mpf.cast.repository.ItemGrupoAtendimentoRepository;

/**
 * Serviço que concentra métodos e lógica de negócio relacionado à entidade ItemGrupoAtendimento
 */
@Service
public class ItemGrupoAtendimentoService {

    @Autowired private ItemGrupoAtendimentoRepository itemGrupoAtendimentoRepository;


    /** Retorna uma estrutura completa de página contendo itens de grupo de atendimento (como Dto)
     * associados a um grupo de atendimento específico.
     * @param pageRequest - Objeto com parâmetros de paginação e ordenação
     * @param idGrupoAtendimento - O id do grupo de atendimento associado
     */
    public Page<ItemGrupoAtendimentoDto> consultarPorGrupoAtendimentoDto(
        PageRequest pageRequest,
        Long idGrupoAtendimento) {

        return itemGrupoAtendimentoRepository.consultarPorGrupoAtendimentoDto(pageRequest, idGrupoAtendimento);
    }


    /** Retorna uma estrutura completa de página contendo itens de grupo de atendimento (como Dto)
     * associados a um serviço específico.
     * @param pageRequest - Objeto com parâmetros de paginação e ordenação
     * @param idServico - O id do serviço associado
     */
    public Page<ItemGrupoAtendimentoDto> consultarPorServicoDto(
        PageRequest pageRequest,
        Long idServico) {

        return itemGrupoAtendimentoRepository.consultarPorGrupoAtendimentoDto(pageRequest, idServico);
    }


    /** Retorna uma estrutura completa de página contendo itens de grupo de atendimento (como Dto), sem filtragem.
     * @param pageRequest - Objeto com parâmetros de paginação e ordenação
     */
    public Page<ItemGrupoAtendimentoDto> consultar(PageRequest pageRequest) {
        return itemGrupoAtendimentoRepository.consultarDto(pageRequest);
    }


    /**
     * Retorna uma listagem ordenada de grupos de atendimento (como Dto)
     * associados a um serviço específico e cujo nome atende ao termo fornecido.
     * Adequado para processar requests estilo autocompletar.
     * @param termo - A parte do nome usada para refinar a busca
     * @param idServico - filtra pelo id do servico (null para não filtrar)
     */
    public List<GrupoAtendimentoMinimoDto> buscarGrupoPeloServicoDto(String termo, Long idServico) {
        List<ItemGrupoAtendimentoDto> itens = itemGrupoAtendimentoRepository
            .buscarPorNomeEServico(termo, idServico);

        return itens.stream().map(item -> item.getGrupoAtendimento()).collect(Collectors.toList());
    }
    /**
     * Retorna o item de grupo de atendimento correspondente ao id fornecido, ou null caso não exista.
     * @param id - O identificador do registro no BD.
     */
    public ItemGrupoAtendimento obter(Long id) {
        return itemGrupoAtendimentoRepository.findById(id).orElse(null);
    }


    /**
     * Persiste um item de grupo de atendimento no BD, retornando o objeto atualizado.
     * @param itemGrupoAtendimento - O objeto a ser persistido.
     */
    public ItemGrupoAtendimento salvar(ItemGrupoAtendimento itemGrupoAtendimento) {
        return itemGrupoAtendimentoRepository.save(itemGrupoAtendimento);
    }


    /**
     * Remove um item de grupo de atendimento do BD, caso exista.
     * @param itemGrupoAtendimento - O objeto que deve ser excluído do BD.
     */
    public void excluir(ItemGrupoAtendimento itemGrupoAtendimento) {
        itemGrupoAtendimentoRepository.delete(itemGrupoAtendimento);
    }

}
