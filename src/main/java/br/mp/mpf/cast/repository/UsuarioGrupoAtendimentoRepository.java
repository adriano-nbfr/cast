package br.mp.mpf.cast.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.mp.mpf.cast.dto.UsuarioGrupoAtendimentoDto;
import br.mp.mpf.cast.model.UsuarioGrupoAtendimento;


@Repository
public interface UsuarioGrupoAtendimentoRepository extends JpaRepository<UsuarioGrupoAtendimento, Long> {

    // Fragmentos que permitem montar consultas controlando o tipo da projeção e filtro,
    // além de garantir o join (quando necessário) para evitar o problema das n + 1 consultag.

    static final String selectEntidade = "select ug ";

    static final String selectListagemDto =
        "select new br.mp.mpf.cast.dto.UsuarioGrupoAtendimentoDto(ug.id, g.id, g.nome, u.id, u.nome, ug.dataInicio, ug.dataFim) " ;

    static final String from =
        "  from UsuarioGrupoAtendimento ug " +
        " inner join ug.grupoAtendimento g " +
        " inner join ug.usuario u ";

    static final String whereFiltrarPorGrupoAtendimento =
        " where g.id = :idGrupoAtendimento ";

    static final String whereFiltrarPorUsuario =
        " where u.id = :idUsuario ";

    static final String whereFiltrarAtivosPorGrupoOuUsuario =
        " where (g.id = :idGrupoAtendimento or :idGrupoAtendimento is null) " +
        "   and (u.id = :idUsuario or :idUsuario is null) " +
        "   and trunc(CURRENT_DATE) between trunc(coalesce(ug.dataInicio, CURRENT_DATE)) and trunc(coalesce(ug.dataFim, CURRENT_DATE))";

    static final String whereBuscarPorNomeEGrupo =
        " where u.ativo = '1' " +
        "   and upper(u.nome) like '%' || upper(:nome) || '%' " +
        "   and (g.id = :idGrupoAtendimento or :idGrupoAtendimento is null) " +
        "   and trunc(CURRENT_DATE) between trunc(coalesce(ug.dataInicio, CURRENT_DATE)) and trunc(coalesce(ug.dataFim, CURRENT_DATE))";

    static final String orderByNomeAsc = " order by u.nome asc";


    // CONVENÇÃO USADA: 'consultar' retorna Page<T>; 'listar' e 'buscar' retornam List<T>


    /** Retorna uma estrutura completa de página contendo associações de usuário com grupo de atendimento (como Dto)
     * para um grupo de atendimento específico.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     * @param idGrupoAtendimento - O id do grupo de atendimento associado
     */
    @Query(selectListagemDto + from + whereFiltrarPorGrupoAtendimento)
    Page<UsuarioGrupoAtendimentoDto> consultarPorGrupoAtendimentoDto(
        Pageable pageable,
        @Param("idGrupoAtendimento") Long idGrupoAtendimento
    );


    /** Retorna uma estrutura completa de página contendo associações de usuário com grupo de atendimento (como Dto)
     * para um usuário específico.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     * @param idUsuario - O id do usuário associado
     */
    @Query(selectListagemDto + from + whereFiltrarPorUsuario)
    Page<UsuarioGrupoAtendimentoDto> consultarPorUsuarioDto(
        Pageable pageable,
        @Param("idUsuario") Long idUsuario
    );


    /** Retorna uma estrutura completa de página contendo as associações ativas considerando a dataInicio e dataFim (como Dto),
     * podendo filtrar por usuário ou grupo de atendimento (ou ambos).
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     * @param idGrupoAtendimento - O id do grupo de atendimento (null para não filtrar)
     * @param idUsuario - O id do usuário (null para não filtrar)
     */
    @Query(selectListagemDto + from + whereFiltrarAtivosPorGrupoOuUsuario)
    Page<UsuarioGrupoAtendimentoDto> consultarAtivosPorGrupoOuUsuarioDto(
        Pageable pageable,
        @Param("idGrupoAtendimento") Long idGrupoAtendimento,
        @Param("idUsuario") Long idUsuario
    );


    /** Retorna uma estrutura completa de página contendo associações de usuário com grupo de atendimento (como Dto), sem filtragem.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     */
    @Query(selectListagemDto + from)
    Page<UsuarioGrupoAtendimentoDto> consultarDto(Pageable pageable);


    /** Retorna uma listagem simples de associações de usuário com grupo de atendimento (como Dto), sem filtragem.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     */
    @Query(selectListagemDto + from)
    List<UsuarioGrupoAtendimentoDto> listarDto(Pageable pageable);


    /** Retorna uma listagem ordenada das associações ativas considerando a dataInicio e dataFim (como Dto),
     * podendo filtrar por usuário ou grupo de atendimento (ou ambos).
     * @param idGrupoAtendimento - O id do grupo de atendimento (null para não filtrar)
     * @param idUsuario - O id do usuário (null para não filtrar)
     */
    @Query(selectListagemDto + from + whereFiltrarAtivosPorGrupoOuUsuario + orderByNomeAsc)
    List<UsuarioGrupoAtendimentoDto> listarAtivosPorGrupoOuUsuarioDto(
        @Param("idGrupoAtendimento") Long idGrupoAtendimento,
        @Param("idUsuario") Long idUsuario
    );


    /**
     * Retorna uma listagem ordenada de usuários ativos cujo nome atende ao termo fornecido.
     * Adequado para processar requests estilo autocompletar.
     * @param nome - A parte do nome usada para refinar a busca
     * @param idGrupoAtendimento - O id do grupo de atendimento (null para não filtrar)
     */
    @Query(selectListagemDto + from + whereBuscarPorNomeEGrupo + orderByNomeAsc)
    List<UsuarioGrupoAtendimentoDto> buscarPorNome(
        @Param("nome") String nome,
        @Param("idGrupoAtendimento") Long idGrupoAtendimento
    );

}
