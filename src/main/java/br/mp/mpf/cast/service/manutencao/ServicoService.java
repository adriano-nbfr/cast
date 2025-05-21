package br.mp.mpf.cast.service.manutencao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.mp.mpf.cast.dto.ServicoListagemDto;
import br.mp.mpf.cast.dto.ServicoMinimoDto;
import br.mp.mpf.cast.exception.RuntimeExceptionTratado;
import br.mp.mpf.cast.model.Servico;
import br.mp.mpf.cast.repository.ServicoRepository;

/**
 * Serviço que concentra métodos e lógica de negócio relacionado à entidade Servico
 */
@Service
public class ServicoService {

    private static final int MAX_RESULTADOS_BUSCA = 100;


    @Autowired private ServicoRepository servicoRepository;


    /** Retorna uma estrutura completa de página contendo serviços (como Dto), com filtragem parametrizada.
     * @param pageRequest - Objeto com parâmetros de paginação e ordenação
     * @param id - filtra pelo id (null para não filtrar)
     * @param nome - filtra pelo nome (vazio para não filtrar)
     * @param ativo - filtra pelo estado de ativo (true/false) (null para não filtrar)
     * @param idCategoria - filtra pelo id da categoria (null para não filtrar)
     */
    public Page<ServicoListagemDto> consultarDto(
        PageRequest pageRequest,
        Long id,
        String nome,
        Boolean ativo,
        Long idCategoria) {

        boolean consultaComFiltro =
            id != null || !nome.isBlank() || ativo != null || idCategoria != null;

        return consultaComFiltro ?
            servicoRepository.consultarDto(pageRequest, id, nome, ativo, idCategoria) :
            servicoRepository.consultarDto(pageRequest);
    }


    /** Retorna uma listagem simples de serviços (como Dto), sem filtragem.
     * @param pageRequest - Objeto com parâmetros de paginação e ordenação
     */
    public List<ServicoListagemDto> listarDto(PageRequest pageRequest) {
        return servicoRepository.listarDto(pageRequest);
    }


    /**
     * Retorna uma listagem ordenada de serviços ativos, filtrando opcionalmente pela categoria.
     * @param idCategoria - filtra pelo id da categoria (null para não filtrar)
     */
    public List<ServicoMinimoDto> listarAtivosPorCategoria(Long idCategoria) {
        return servicoRepository.listarAtivosPorCategoriaDto(idCategoria);
    }


    /**
     * Retorna uma listagem ordenada de serviços ativos cujo nome atende ao termo fornecido,
     * filtrando opcionalmente pela categoria.
     * Adequado para processar requests estilo autocompletar.
     * @param termo - A parte do nome usada para refinar a busca
     * @param idCategoria - filtra pelo id da categoria (null para não filtrar)
     */
    public List<ServicoMinimoDto> buscarDto(String termo, Long idCategoria) {
        List<ServicoMinimoDto> itens = servicoRepository.buscarPorNome(termo, idCategoria);

        if (itens.size() > MAX_RESULTADOS_BUSCA)
            throw new RuntimeExceptionTratado("A busca resultou em um grande número de itens. Refine-a um pouco mais.");

        return itens;
    }


    /**
     * Retorna o serviço correspondente ao id fornecido, ou null caso não exista.
     * @param id - O identificador do registro no BD.
     */
    public Servico obter(Long id) {
        return servicoRepository.findById(id).orElse(null);
    }


    /**
     * Persiste um serviço no BD, retornando o objeto atualizado.
     * @param servico - O objeto a ser persistido.
     */
    public Servico salvar(Servico servico) {
        return servicoRepository.save(servico);
    }


    /**
     * Remove um serviço do BD, caso exista.
     * @param servico - O objeto que deve ser excluído do BD.
     */
    public void excluir(Servico servico) {
        servicoRepository.delete(servico);
    }


    /**
     * Realiza uma exclusão em lote dos serviços correpondentes aos ids fornecidos.
     * @param ids - Os ids dos registros a serem excluídos do BD.
     */
    public List<Long> excluirEmLote(List<Long> ids) {
        try {
            servicoRepository.deleteAllById(ids);
            return ids; // Em uma operação individual, deveria retornar os ids com sucesso.
        }
        catch (Exception e) {
            throw new RuntimeExceptionTratado("Falha ao tentar excluir múltiplos serviços.");
        }
    }
}
