package br.mp.mpf.cast.service.manutencao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.mp.mpf.cast.dto.CategoriaListagemDto;
import br.mp.mpf.cast.dto.CategoriaMinimoDto;
import br.mp.mpf.cast.exception.RuntimeExceptionTratado;
import br.mp.mpf.cast.model.Categoria;
import br.mp.mpf.cast.repository.CategoriaRepository;

/**
 * Serviço que concentra métodos e lógica de negócio relacionado à entidade Categoria
 */
@Service
public class CategoriaService {

    @Autowired private CategoriaRepository categoriaRepository;


    /** Retorna uma estrutura completa de página contendo categorias (como Dto), com filtragem parametrizada.
     * @param pageRequest - Objeto com parâmetros de paginação e ordenação
     * @param id - filtra pelo id (null para não filtrar)
     * @param nome - filtra pelo nome (vazio para não filtrar)
     * @param ativo - filtra pelo estado de ativo (true/false) (null para não filtrar)
     */
    public Page<CategoriaListagemDto> consultarDto(
        PageRequest pageRequest,
        Long id,
        String nome,
        Boolean ativo) {

        boolean consultaComFiltro =
            id != null || !nome.isBlank() || ativo != null;

        return consultaComFiltro ?
            categoriaRepository.consultarDto(pageRequest, id, nome, ativo) :
            categoriaRepository.consultarDto(pageRequest);
    }


    /** Retorna uma listagem simples de categorias (como Dto), sem filtragem.
     * @param pageRequest - Objeto com parâmetros de paginação e ordenação
     */
    public List<CategoriaListagemDto> listarDto(PageRequest pageRequest) {
        return categoriaRepository.listarDto(pageRequest);
    }


    /** Retorna uma listagem ordenada de todas categorias (como Dto). */
    public List<CategoriaListagemDto> listarTudoOrdenadoPorNomeDto() {
        return categoriaRepository.listarTudoOrdenadoPorNomeDto();
    }


    /** Retorna uma listagem ordenada de categorias ativas (como Dto). */
    public List<CategoriaListagemDto> listarAtivosOrdenadoPorNomeDto() {
        return categoriaRepository.listarAtivosOrdenadosPorNomeDto();
    }


    /**
     * Retorna uma listagem ordenada de categorias ativas cujo nome atende ao termo fornecido.
     * Adequado para processar requests estilo autocompletar.
     * @param nome - A parte do nome usada para refinar a busca
     */
    public List<CategoriaMinimoDto> buscarDto(String nome) {
        return categoriaRepository.buscarPorNome(nome);
    }


    /**
     * Retorna a categoria correspondente ao id fornecido, ou null caso não exista.
     * @param id - O identificador do registro no BD.
     */
    public Categoria obter(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }


    /**
     * Persiste uma categoria no BD, retornando o objeto atualizado.
     * @param categoria - O objeto a ser persistido.
     */
    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }


    /**
     * Remove uma categoria do BD, caso exista.
     * @param categoria - O objeto que deve ser excluído do BD.
     */
    public void excluir(Categoria categoria) {
        categoriaRepository.delete(categoria);
    }


    /**
     * Realiza uma exclusão em lote das categorias correpondentes aos ids fornecidos.
     * @param ids - Os ids dos registros a serem excluídos do BD.
     */
    public List<Long> excluirEmLote(List<Long> ids) {
        try {
            categoriaRepository.deleteAllById(ids);
            return ids; // Em uma operação individual, deveria retornar os ids com sucesso.
        }
        catch (Exception e) {
            throw new RuntimeExceptionTratado("Falha ao tentar excluir múltiplas categorias.");
        }
    }
}
