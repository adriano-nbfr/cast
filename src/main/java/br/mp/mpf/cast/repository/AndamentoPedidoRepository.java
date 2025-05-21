package br.mp.mpf.cast.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.mp.mpf.cast.dto.AndamentoPedidoDto;
import br.mp.mpf.cast.model.AndamentoPedido;
import br.mp.mpf.cast.model.ArquivoAndamento;

@Repository
public interface AndamentoPedidoRepository extends JpaRepository<AndamentoPedido, Long> {

    static final String selectEntidade = "select ap ";

    static final String selectListagemDto =
        "select new br.mp.mpf.cast.dto.AndamentoPedidoDto(ap.id, ap.descricao, ap.dataRegistro, ap.usuario.id, ap.usuario.nome, aa.id, aa.nomeArquivo) ";

    static final String from =
        "  from ArquivoAndamento aa right join aa.andamentoPedido ap inner join ap.pedido p";

    static final String wherePorPedido =
        " where p.id = :idPedido ";

    static final String orderByDataDesc =
        " order by ap.dataRegistro desc" ;


    // CONVENÇÃO USADA: 'consultar' retorna Page<T>; 'listar' e 'buscar' retornam List<T>


    /**
     * Retorna uma estrutura completa de página contendo os andamentos de um pedudi (como Dto)
     * @param pageable -  Objeto com parâmetros de paginação e ordenação
     * @param idPedido - Id do pedido a ser filtrado
     * @return - A página com os andamentos associados
     */
    @Query(selectListagemDto + from + wherePorPedido)
    Page<AndamentoPedidoDto> consultarPorPedidoDto(
        Pageable pageable,
        @Param("idPedido") Long idPedido
    );


    /* Retorna a lista dos andamentos de uma pedido (como Dto) ordenados por data mais recente primeiro.
     * @param idPedido - Id do pedido a ser filtrado
     * @return - A lista com os andamentos associados
     */
    @Query(selectListagemDto + from + wherePorPedido + orderByDataDesc)
    List<AndamentoPedidoDto> listarPorPedidoDto(
        @Param("idPedido") Long idPedido
    );


    @Query("""
        select a from ArquivoAndamento a
         where a.andamentoPedido.id = :idAndamentoPedido
         and (a.id = :idArquivo or :idArquivo is null)
        """)
    List<ArquivoAndamento> listarArquivosAndamento(
        @Param("idAndamentoPedido") Long idAndamentoPedido,
        @Param("idArquivo") Long idArquivo
    );

}
