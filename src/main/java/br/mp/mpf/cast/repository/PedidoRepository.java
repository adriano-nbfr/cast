package br.mp.mpf.cast.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.mp.mpf.cast.dto.PedidoListagemDto;
import br.mp.mpf.cast.model.Pedido;
import br.mp.mpf.cast.model.tipos.UrgenciaPedido;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Fragmentos que permitem montar consultas controlando o tipo da projeção e filtro,
    // além de garantir o join (quando necessário) para evitar o problema das n + 1 consultas.

    static final String selectEntidade = "select p ";

    static final String selectListagemDto =
        "select new br.mp.mpf.cast.dto.PedidoListagemDto(" +
        " p.id, s.id, s.nome, us.id, us.nome, ua.id, ua.nome," +
        " p.titulo, p.descricao, p.urgencia, p.status, p.dataAbertura, p.dataAbertura) ";

    static final String from =
        " from Pedido p " +
        " inner join p.servico s " +
        " inner join p.usuarioSolicitante us " +
        " left join p.usuarioAtendente ua " +
        " left join p.grupoAtendimento g ";

    static final String whereComFiltro =
        " where (p.id = :id or :id is null)" +
        "   and (upper(p.titulo || ' ' || p.descricao) like '%' || upper(:texto) || '%' or trim(:texto) = '')" +
        "   and (p.dataFechamento is null or :status = 'F') " +
        "   and (p.status = :status or :status is null) " +
        "   and (p.urgencia = :urgencia or :urgencia is null) " +
        "   and (trunc(p.dataAbertura) between trunc(coalesce(:dataInicial, p.dataAbertura)) and trunc(coalesce(:dataFinal, p.dataAbertura))) " +
        "   and (s.id = :idServico or :idServico is null) " +
        "   and (g.id = :idGrupoAtendimento or :idGrupoAtendimento is null) " +
        "   and (us.id = :idUsuarioSolicitante or :idUsuarioSolicitante is null) " +
        "   and (ua.id = :idUsuarioAtendente or :idUsuarioAtendente is null) ";

    static final String orderByDataAberturaDesc = " order by p.dataAbertura desc";

    static final String orderByUrgenciaDataAberturaDesc = " order by p.urgencia desc, p.dataAbertura desc";

    static final String whereFiltrarPorUsuarioSolicitante =
        " where us.id = :idUsuarioSolicitante ";

    static final String whereFiltrarPorUsuarioAtendente =
        " where ua.id = :idUsuarioAtendente ";


    // CONVENÇÃO USADA: 'consultar' retorna Page<T>; 'listar' e 'buscar' retornam List<T>

    /** Retorna uma estrutura completa de página contendo pedidos (como Dto), com filtragem parametrizada.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     * @param id - filtra pelo id (null para não filtrar)
     * @param texto - filtra pelo texto contido no título ou na descrição (vazio para não filtrar)
     * @param urgencia - filtra pela urgência (null para não filtrar)
     * @param status - filtra pelo status (null para não filtrar)
     * @param dataInicial - filtra pela data de abertura a partir da data informada (null para não filtrar)
     * @param dataFinal - filtra pela data de abertura até a data informada (null para não filtrar)
     * @param idServico - filtra pelo servico vinculado (null para não filtrar)
     * @param idGrupoAtendimento - filtra pelo grupo de atendimento associado (null para não filtrar)
     * @param idUsuarioSolicitante - filtra pelo usuário solicitante (null para não filtrar)
     * @param idUsuarioAtendente - filtra pelo usuário atendente (null para não filtrar)
     */
    @Query(selectListagemDto + from + whereComFiltro)
    Page<PedidoListagemDto> consultarDto(
        Pageable pageable,
        @Param("id") Long id,
        @Param("texto") String texto,
        @Param("urgencia") UrgenciaPedido urgencia,
        @Param("status") String status,
        @Param("dataInicial") LocalDate dataInicial,
        @Param("dataFinal") LocalDate dataFinal,
        @Param("idServico") Long idServico,
        @Param("idGrupoAtendimento") Long idGrupoAtendimento,
        @Param("idUsuarioSolicitante") Long idUsuarioSolicitante,
        @Param("idUsuarioAtendente") Long idUsuarioAtendente
    );


    /** Retorna uma listagem simples contendo pedidos (como Dto) ordenados por urgencia, com filtragem parametrizada.
     * @param id - filtra pelo id (null para não filtrar)
     * @param texto - filtra pelo texto contido no título ou na descrição (vazio para não filtrar)
     * @param urgencia - filtra pela urgência (null para não filtrar)
     * @param status - filtra pelo status (null para não filtrar)
     * @param dataInicial - filtra pela data de abertura a partir da data informada (null para não filtrar)
     * @param dataFinal - filtra pela data de abertura até a data informada (null para não filtrar)
     * @param idServico - filtra pelo servico vinculado (null para não filtrar)
     * @param idGrupoAtendimento - filtra pelo grupo de atendimento associado (null para não filtrar)
     * @param idUsuarioSolicitante - filtra pelo usuário solicitante (null para não filtrar)
     * @param idUsuarioAtendente - filtra pelo usuário atendente (null para não filtrar)
     */
    @Query(selectListagemDto + from + whereComFiltro + orderByUrgenciaDataAberturaDesc)
    List<PedidoListagemDto> listarDtoOrdenadoPorUrgencia(
        @Param("id") Long id,
        @Param("texto") String texto,
        @Param("urgencia") UrgenciaPedido urgencia,
        @Param("status") String status,
        @Param("dataInicial") LocalDate dataInicial,
        @Param("dataFinal") LocalDate dataFinal,
        @Param("idServico") Long idServico,
        @Param("idGrupoAtendimento") Long idGrupoAtendimento,
        @Param("idUsuarioSolicitante") Long idUsuarioSolicitante,
        @Param("idUsuarioAtendente") Long idUsuarioAtendente
    );


    /** Retorna uma listagem simples contendo pedidos (como Dto) ordenados por data de abertura, com filtragem parametrizada.
     * @param id - filtra pelo id (null para não filtrar)
     * @param texto - filtra pelo texto contido no título ou na descrição (vazio para não filtrar)
     * @param urgencia - filtra pela urgência (null para não filtrar)
     * @param status - filtra pelo status (null para não filtrar)
     * @param dataInicial - filtra pela data de abertura a partir da data informada (null para não filtrar)
     * @param dataFinal - filtra pela data de abertura até a data informada (null para não filtrar)
     * @param idServico - filtra pelo servico vinculado (null para não filtrar)
     * @param idGrupoAtendimento - filtra pelo grupo de atendimento associado (null para não filtrar)
     * @param idUsuarioSolicitante - filtra pelo usuário solicitante (null para não filtrar)
     * @param idUsuarioAtendente - filtra pelo usuário atendente (null para não filtrar)
     */
    @Query(selectListagemDto + from + whereComFiltro + orderByDataAberturaDesc)
    List<PedidoListagemDto> listarDtoOrdenadoPorDataAbertura(
        @Param("id") Long id,
        @Param("texto") String texto,
        @Param("urgencia") UrgenciaPedido urgencia,
        @Param("status") String status,
        @Param("dataInicial") LocalDate dataInicial,
        @Param("dataFinal") LocalDate dataFinal,
        @Param("idServico") Long idServico,
        @Param("idGrupoAtendimento") Long idGrupoAtendimento,
        @Param("idUsuarioSolicitante") Long idUsuarioSolicitante,
        @Param("idUsuarioAtendente") Long idUsuarioAtendente
    );

}