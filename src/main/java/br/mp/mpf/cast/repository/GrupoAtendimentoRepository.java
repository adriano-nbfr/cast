package br.mp.mpf.cast.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.mp.mpf.cast.dto.GrupoAtendimentoDto;
import br.mp.mpf.cast.dto.GrupoAtendimentoMinimoDto;
import br.mp.mpf.cast.model.GrupoAtendimento;


@Repository
public interface GrupoAtendimentoRepository extends JpaRepository<GrupoAtendimento, Long> {

    // Fragmentos que permitem montar consultas controlando o tipo da projeção e filtro,
    // além de garantir o join (quando necessário) para evitar o problema das n + 1 consultag.

    static final String selectEntidade = "select g ";

    static final String selectListagemDto =
        "select new br.mp.mpf.cast.dto.GrupoAtendimentoDto(g.id, g.nome, g.ativo, c.id, c.nome) ";

    static final String selectMinimoDto =
        "select new br.mp.mpf.cast.dto.GrupoAtendimentoMinimoDto(g.id, g.nome) ";

    static final String from =
        "  from GrupoAtendimento g left outer join g.categoria c ";

    static final String whereAtivo =
        " where g.ativo = 1 ";

    static final String whereComFiltro =
        " where (g.id = :id or :id is null)" +
        "   and (upper(g.nome) like '%' || upper(:nome) || '%' or trim(:nome) = '')" +
        "   and (g.ativo = :ativo or :ativo is null) " +
        "   and (c.id = :idCategoria or :idCategoria is null) ";

    static final String whereBuscarPorNome =
        " where g.ativo = 1 " +
        "   and upper(g.nome) like '%' || upper(:nome) || '%'" +
        "   and (c.id = :idCategoria or :idCategoria is null) ";

    static final String orderByNomeAsc = " order by g.nome asc";


    // CONVENÇÃO USADA: 'consultar' retorna Page<T>; 'listar' e 'buscar' retornam List<T>


    /** Retorna uma estrutura completa de página contendo grupos de atendimento (como Dto), com filtragem parametrizada.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     * @param id - filtra pelo id (null para não filtrar)
     * @param nome - filtra pelo nome (vazio para não filtrar)
     * @param ativo - filtra pelo estado de ativo (true/false) (null para não filtrar)
     * @param idCategoria - filtra pelo id da categoria (null para não filtrar)
     */
    @Query(selectListagemDto + from + whereComFiltro)
    Page<GrupoAtendimentoDto> consultarDto(
        Pageable pageable,
        @Param("id") Long id,
        @Param("nome") String nome,
        @Param("ativo") Boolean ativo,
        @Param("idCategoria") Long idCategoria
    );


    /** Retorna uma estrutura completa de página contendo grupos de atendimento (como Dto), sem filtragem.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     */
    @Query(selectListagemDto + from)
    Page<GrupoAtendimentoDto> consultarDto(Pageable pageable);


    /** Retorna uma listagem simples de grupos de atendimento (como Dto), sem filtragem.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     */
    @Query(selectListagemDto + from)
    List<GrupoAtendimentoDto> listarDto(Pageable pageable);


    /**
     * Retorna uma listagem ordenada de grupos de atendimento ativos cujo nome atende ao termo fornecido,
     * filtrando opcionalmente pela categoria.
     * Adequado para processar requests estilo autocompletar.
     * @param nome - A parte do nome usada para refinar a busca
     * @param idCategoria - filtra pelo id da categoria (null para não filtrar)
     */
    @Query(selectMinimoDto + from + whereBuscarPorNome + orderByNomeAsc)
    List<GrupoAtendimentoMinimoDto> buscarPorNome(
        @Param("nome") String nome,
        @Param("idCategoria") Long idCategoria
    );

}
