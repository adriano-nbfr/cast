package br.mp.mpf.cast.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.mp.mpf.cast.dto.ServicoListagemDto;
import br.mp.mpf.cast.dto.ServicoMinimoDto;
import br.mp.mpf.cast.model.Servico;


@Repository
public interface ServicoRepository  extends JpaRepository<Servico, Long> {

    // Fragmentos que permitem montar consultas controlando o tipo da projeção e filtro,
    // além de garantir o join (quando necessário) para evitar o problema das n + 1 consultas.

    static final String selectEntidade = "select s ";

    static final String selectListagemDto =
        "select new br.mp.mpf.cast.dto.ServicoListagemDto(s.id, s.nome, s.ativo, c.id, c.nome) ";

    static final String selectMinimoDto =
        "select new br.mp.mpf.cast.dto.ServicoMinimoDto(s.id, s.nome) ";

    static final String from =
        "  from Servico s inner join s.categoria c ";

    static final String whereAtivo =
        " where s.ativo = 1 ";

    static final String whereAtivoPorCategoria =
        " where s.ativo = 1 and (c.id = :idCategoria or :idCategoria is null)";

    static final String whereComFiltro =
        " where (s.id = :id or :id is null)" +
        "   and (upper(s.nome) like '%' || upper(:nome) || '%' or trim(:nome) = '')" +
        "   and (s.ativo = :ativo or :ativo is null) " +
        "   and (c.id = :idCategoria or :idCategoria is null) ";

    static final String whereBuscarPorNome =
        " where s.ativo = 1 " +
        "   and upper(s.nome) like '%' || upper(:nome) || '%'" +
        "   and (c.id = :idCategoria or :idCategoria is null) ";

    static final String orderByNomeAsc = " order by s.nome asc";


    // CONVENÇÃO USADA: 'consultar' retorna Page<T>; 'listar' e 'buscar' retornam List<T>


    /** Retorna uma estrutura completa de página contendo serviços (como Dto), com filtragem parametrizada.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     * @param id - filtra pelo id (null para não filtrar)
     * @param nome - filtra pelo nome (vazio para não filtrar)
     * @param ativo - filtra pelo estado de ativo (true/false) (null para não filtrar)
     * @param idCategoria - filtra pelo id da categoria (null para não filtrar)
     */
    @Query(selectListagemDto + from + whereComFiltro)
    Page<ServicoListagemDto> consultarDto(
        Pageable pageable,
        @Param("id") Long id,
        @Param("nome") String nome,
        @Param("ativo") Boolean ativo,
        @Param("idCategoria") Long idCategoria
    );


    /** Retorna uma estrutura completa de página contendo serviços (como Dto), sem filtragem.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     */
    @Query(selectListagemDto + from)
    Page<ServicoListagemDto> consultarDto(Pageable pageable);


    /** Retorna uma listagem simples de serviços (como Dto), sem filtragem.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     */
    @Query(selectListagemDto + from)
    List<ServicoListagemDto> listarDto(Pageable pageable);


    /** Retorna uma listagem simples de serviços (como Dto), filtrando opcionalmente por categoria.
     * @param idCategoria - filtra pelo id da categoria (null para não filtrar)
     */
    @Query(selectMinimoDto + from + whereAtivoPorCategoria + orderByNomeAsc)
    List<ServicoMinimoDto> listarAtivosPorCategoriaDto(Long idCategoria);


    /**
     * Retorna uma listagem ordenada de serviços ativos cujo nome atende ao termo fornecido,
     * filtrando opcionalmente pela categoria.
     * Adequado para processar requests estilo autocompletar.
     * @param nome - A parte do nome usada para refinar a busca
     * @param idCategoria - filtra pelo id da categoria (null para não filtrar)
     */
    @Query(selectMinimoDto + from + whereBuscarPorNome + orderByNomeAsc)
    List<ServicoMinimoDto> buscarPorNome(
        @Param("nome") String nome,
        @Param("idCategoria") Long idCategoria
    );

}
