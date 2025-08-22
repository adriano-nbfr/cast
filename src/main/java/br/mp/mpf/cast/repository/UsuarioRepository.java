package br.mp.mpf.cast.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.mp.mpf.cast.dto.UsuarioDto;
import br.mp.mpf.cast.dto.UsuarioMinimoDto;
import br.mp.mpf.cast.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Fragmentos que permitem montar consultas controlando o tipo da projeção e filtro,
    // além de garantir o join (quando necessário) para evitar o problema das n + 1 consultas.

    static final String selectListagemDto =
        "select new br.mp.mpf.cast.dto.UsuarioDto(u.id, u.nome, u.email) ";

    static final String selectMinimoDto =
        "select new br.mp.mpf.cast.dto.UsuarioMinimoDto(u.id, u.nome) ";

    static final String from =
        "  from Usuario u ";

    static final String whereAtivo =
        " where u.ativo = '1' ";

    static final String whereAtivoComFiltro =
        " where u.ativo = '1' " +
        "   and (u.id = :id or :id is null)" +
        "   and (upper(u.nome) like '%' || upper(:nome) || '%' or trim(:nome) = '')" +
        "   and (upper(u.email) like '%' || upper(:email) || '%' or trim(:email) = '') ";

    static final String whereBuscarPorNome =
        " where u.ativo = '1' " +
        "   and upper(u.nome) like '%' || upper(:nome) || '%' ";

    static final String orderByNomeAsc = " order by u.nome asc";


    // CONVENÇÃO USADA: 'consultar' retorna Page<T>; 'listar' e 'buscar' retornam List<T>


    /** Retorna uma estrutura completa de página contendo usuários ativos (como Dto), com filtragem parametrizada.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     * @param id - filtra pelo id (null para não filtrar)
     * @param nome - filtra pelo nome (vazio para não filtrar)
     * @param email - filtra pelo e-mail (vazio para não filtrar)
     */
    @Query(selectListagemDto + from + whereAtivoComFiltro)
    Page<UsuarioDto> consultarAtivosDto(
        Pageable pageable,
        @Param("id") Long id,
        @Param("nome") String nome,
        @Param("email") String email
    );


    /** Retorna uma estrutura completa de página contendo usuários ativos (como Dto), sem filtragem.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     */
    @Query(selectListagemDto + from + whereAtivo)
    Page<UsuarioDto> consultarAtivosDto(Pageable pageable);


    /** Retorna uma listagem simples de usuários ativos (como Dto), sem filtragem.
     * @param pageable - Objeto com parâmetros de paginação e ordenação
     */
    @Query(selectListagemDto + from + whereAtivo)
    List<UsuarioDto> listarAtivosDto(Pageable pageable);


    /**
     * Retorna uma listagem ordenada de usuários ativos cujo nome atende ao termo fornecido.
     * Adequado para processar requests estilo autocompletar.
     * @param nome - A parte do nome usada para refinar a busca
     */
    @Query(selectMinimoDto + from + whereBuscarPorNome + orderByNomeAsc)
    List<UsuarioMinimoDto> buscarPorNome(@Param("nome") String nome);


    /**
     * Retorna o usuário cujo e-mail corresponde ao valor fornecido, ou null caso não encontre nenhum.
     * @param email - O e-mail usado para encontrar o usuário.
     */
    Usuario findByEmailIgnoreCase(String email);

}
