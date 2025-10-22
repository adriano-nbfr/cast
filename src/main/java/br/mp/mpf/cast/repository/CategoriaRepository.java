package br.mp.mpf.cast.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.mp.mpf.cast.dto.CategoriaListagemDto;
import br.mp.mpf.cast.dto.CategoriaMinimoDto;
import br.mp.mpf.cast.model.Categoria;


@Repository
public interface CategoriaRepository  extends JpaRepository<Categoria, Long> {

    // Fragmentos que permitem montar consultas controlando o tipo da projeção e filtro,
    // além de garantir o join (quando necessário) para evitar o problema das n + 1 consultas.

    static final String selectEntidade = "select c ";

    static final String selectListagemDto =
        "select new br.mp.mpf.cast.dto.CategoriaListagemDto(c.id, c.nome, c.ativo) ";

    static final String selectMinimoDto =
        "select new br.mp.mpf.cast.dto.CategoriaMinimoDto(c.id, c.nome) ";

    static final String from = " from Categoria c ";

    static final String whereAtivo =
        " where c.ativo = 1 ";

    static final String whereComFiltro =
        " where (c.id = :id or :id is null)" +
        "   and (upper(c.nome) like '%' || upper(:nome) || '%' or trim(:nome) = '')" +
        "   and (c.ativo = :ativo or :ativo is null) ";

    static final String whereBuscarPorNome =
        " where c.ativo = 1 " +
        "   and upper(c.nome) like '%' || upper(:nome) || '%' ";

    static final String orderByNomeAsc = " order by c.nome asc";


    // CONVENÇÃO USADA: 'consultar' retorna Page<T>; 'listar' e 'buscar' retornam List<T>


    /** Retorna uma estrutura completa de página contendo categorias (como Dto), com filtragem parametrizada.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     * @param id - filtra pelo id (null para não filtrar)
     * @param nome - filtra pelo nome (vazio para não filtrar)
     * @param ativo - filtra pelo estado de ativo (true/false) (null para não filtrar)
     */
    @Query(selectListagemDto + from + whereComFiltro)
    Page<CategoriaListagemDto> consultarDto(
        Pageable pageable,
        @Param("id") Long id,
        @Param("nome") String nome,
        @Param("ativo") Boolean ativo
    );


    /** Retorna uma estrutura completa de página contendo categorias (como Dto), sem filtragem.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     */
    @Query(selectListagemDto + from)
    Page<CategoriaListagemDto> consultarDto(Pageable pageable);


    /** Retorna uma listagem simples de categorias (como Dto), sem filtragem.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     */
    @Query(selectListagemDto + from)
    List<CategoriaListagemDto> listarDto(Pageable pageable);


    /** Retorna uma listagem ordenada de todas as categorias (como Dto).
     */
    @Query(selectListagemDto + from + orderByNomeAsc)
    List<CategoriaListagemDto> listarTudoOrdenadoPorNomeDto();


    /** Retorna uma listagem ordenada de categorias ativas (como Dto).
     */
    @Query(selectListagemDto + from + whereAtivo + orderByNomeAsc)
    List<CategoriaListagemDto> listarAtivosOrdenadosPorNomeDto();


    /**
     * Retorna uma listagem ordenada de categorias ativas cujo nome atende ao termo fornecido.
     * Adequado para processar requests estilo autocompletar.
     * @param nome - A parte do nome usada para refinar a busca
     */
    @Query(selectMinimoDto + from + whereBuscarPorNome + orderByNomeAsc)
    List<CategoriaMinimoDto> buscarPorNome(@Param("nome") String nome);

}
