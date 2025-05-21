package br.mp.mpf.cast.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.mp.mpf.cast.dto.ItemGrupoAtendimentoDto;
import br.mp.mpf.cast.model.GrupoAtendimento;
import br.mp.mpf.cast.model.ItemGrupoAtendimento;


@Repository
public interface ItemGrupoAtendimentoRepository extends JpaRepository<ItemGrupoAtendimento, Long> {

    // Fragmentos que permitem montar consultas controlando o tipo da projeção e filtro,
    // além de garantir o join (quando necessário) para evitar o problema das n + 1 consultag.

    static final String selectEntidade = "select i ";

    static final String selectListagemDto =
        "select new br.mp.mpf.cast.dto.ItemGrupoAtendimentoDto(i.id, g.id, g.nome, s.id, s.nome) ";

    static final String from =
        "  from ItemGrupoAtendimento i " +
        " inner join i.grupoAtendimento g " +
        " inner join i.servico s ";

    static final String whereFiltrarPorGrupoAtendimento =
        " where g.id = :idGrupoAtendimento ";

    static final String whereFiltrarPorServico =
        " where s.id = :idServico ";

    static final String whereBuscarPorNomeEServico =
        " where g.ativo = '1' " +
        "   and upper(g.nome) like '%' || upper(:nome) || '%' " +
        "   and (s.id = :idServico or :idServico is null) ";

    static final String orderByNomeAsc = " order by g.nome asc";


    // CONVENÇÃO USADA: 'consultar' retorna Page<T>; 'listar' e 'buscar' retornam List<T>


    /** Retorna uma estrutura completa de página contendo itens de grupo de atendimento (como Dto)
     * associados a um grupo de atendimento específico.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     * @param idGrupoAtendimento - O id do grupo de atendimento associado
     */
    @Query(selectListagemDto + from + whereFiltrarPorGrupoAtendimento)
    Page<ItemGrupoAtendimentoDto> consultarPorGrupoAtendimentoDto(
        Pageable pageable,
        @Param("idGrupoAtendimento") Long idGrupoAtendimento
    );


    /** Retorna uma estrutura completa de página contendo itens de grupo de atendimento (como Dto)
     * associados a um serviço específico.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     * @param idServico - O id do serviço associado
     */
    @Query(selectListagemDto + from + whereFiltrarPorServico)
    Page<ItemGrupoAtendimentoDto> consultarPorServicoDto(
        Pageable pageable,
        @Param("idServico") Long idServico
    );


    /** Retorna uma estrutura completa de página contendo itens de grupo de atendimento (como Dto), sem filtragem.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     */
    @Query(selectListagemDto + from)
    Page<ItemGrupoAtendimentoDto> consultarDto(Pageable pageable);


    @Query("select i.grupoAtendimento " + from + whereFiltrarPorServico + orderByNomeAsc)
    List<GrupoAtendimento> listarGruposPeloServico(Long idServico);

    /**
     * Retorna uma listagem ordenada de associaçoes de grupo de atendimento com serviço,
     * cujo nome atende ao termo fornecido.
     * Adequado para processar requests estilo autocompletar.
     * @param nome - A parte do nome usada para refinar a busca
     * @param idServico - O id do serviço (null para não filtrar)
     */
    @Query(selectListagemDto + from + whereBuscarPorNomeEServico + orderByNomeAsc)
    List<ItemGrupoAtendimentoDto> buscarPorNomeEServico(
        @Param("nome") String nome,
        @Param("idServico") Long idServico
    );


}
