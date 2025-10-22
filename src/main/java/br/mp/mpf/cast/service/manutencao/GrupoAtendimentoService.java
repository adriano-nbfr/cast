package br.mp.mpf.cast.service.manutencao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.mp.mpf.cast.dto.GrupoAtendimentoDto;
import br.mp.mpf.cast.dto.GrupoAtendimentoMinimoDto;
import br.mp.mpf.cast.exception.RuntimeExceptionTratado;
import br.mp.mpf.cast.model.GrupoAtendimento;
import br.mp.mpf.cast.repository.GrupoAtendimentoRepository;

/**
 * Serviço que concentra métodos e lógica de negócio relacionado à entidade GrupoAtendimento
 */
@Service
public class GrupoAtendimentoService {

    @Autowired private GrupoAtendimentoRepository grupoAtendimentoRepository;


    /** Retorna uma estrutura completa de página contendo grupos de atendimento (como Dto), com filtragem parametrizada.
     * @param pageRequest - Objeto com parâmetros de paginação e ordenação
     * @param id - filtra pelo id (null para não filtrar)
     * @param nome - filtra pelo nome (vazio para não filtrar)
     * @param ativo - filtra pelo estado de ativo (true/false) (null para não filtrar)
     * @param idCategoria - filtra pelo id da categoria (null para não filtrar)
     */
    public Page<GrupoAtendimentoDto> consultarDto(
        PageRequest pageRequest,
        Long id,
        String nome,
        Boolean ativo,
        Long idCategoria) {

        boolean consultaComFiltro =
            id != null || !nome.isBlank() || ativo != null || idCategoria != null;

        return consultaComFiltro ?
            grupoAtendimentoRepository.consultarDto(pageRequest, id, nome, ativo, idCategoria) :
            grupoAtendimentoRepository.consultarDto(pageRequest);
    }


    /** Retorna uma listagem simples de grupos de atendimento (como Dto), sem filtragem.
     * @param pageRequest - Objeto com parâmetros de paginação e ordenação
     */
    public List<GrupoAtendimentoDto> listarDto(PageRequest pageRequest) {
        return grupoAtendimentoRepository.listarDto(pageRequest);
    }


    /**
     * Retorna uma listagem ordenada de grupos de atendimento ativos cujo nome atende ao termo fornecido,
     * filtrando opcionalmente pela categoria.
     * Adequado para processar requests estilo autocompletar.
     * @param nome - A parte do nome usada para refinar a busca
     * @param idCategoria - filtra pelo id da categoria (null para não filtrar)
     */
    public List<GrupoAtendimentoMinimoDto> buscarDto(String nome, Long idCategoria) {
        return grupoAtendimentoRepository.buscarPorNome(nome, idCategoria);
    }


    /**
     * Retorna o grupo de atendimento correspondente ao id fornecido, ou null caso não exista.
     * @param id - O identificador do registro no BD.
     */
    public GrupoAtendimento obter(Long id) {
        return grupoAtendimentoRepository.findById(id).orElse(null);
    }


    /**
     * Persiste um grupo de atendimento no BD, retornando o objeto atualizado.
     * @param grupoAtendimento - O objeto a ser persistido.
     */
    public GrupoAtendimento salvar(GrupoAtendimento grupoAtendimento) {
        return grupoAtendimentoRepository.save(grupoAtendimento);
    }


    /**
     * Remove um grupo de atendimento do BD, caso exista.
     * @param grupoAtendimento - O objeto que deve ser excluído do BD.
     */
    public void excluir(GrupoAtendimento grupoAtendimento) {
        grupoAtendimentoRepository.delete(grupoAtendimento);
    }


    /**
     * Realiza uma exclusão em lote dos grupos de atendimento correpondentes aos ids fornecidos.
     * @param ids - Os ids dos registros a serem excluídos do BD.
     */
    public List<Long> excluirEmLote(List<Long> ids) {
        try {
            grupoAtendimentoRepository.deleteAllById(ids);
            return ids; // Em uma operação individual, deveria retornar os ids com sucesso.
        }
        catch (Exception e) {
            throw new RuntimeExceptionTratado("Falha ao tentar excluir múltiplos grupos de atendimento.");
        }
    }
}
