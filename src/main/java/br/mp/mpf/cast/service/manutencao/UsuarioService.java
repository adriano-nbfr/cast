package br.mp.mpf.cast.service.manutencao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.mp.mpf.cast.dto.UsuarioDto;
import br.mp.mpf.cast.dto.UsuarioMinimoDto;
import br.mp.mpf.cast.exception.RuntimeExceptionTratado;
import br.mp.mpf.cast.model.Usuario;
import br.mp.mpf.cast.repository.UsuarioRepository;

/**
 * Serviço que concentra métodos e lógica de negócio relacionado à entidade Usuario
 */
@Service
public class UsuarioService {

    private static final int MAX_RESULTADOS_BUSCA = 100;


    @Autowired private UsuarioRepository usuarioModelRepository;


    /** Retorna uma estrutura completa de página contendo usuários (como Dto), com filtragem parametrizada.
     * @param pageRequest - Objeto com parâmetros de paginação e ordenação
     * @param id - filtra pelo id (null para não filtrar)
     * @param nome - filtra pelo nome (vazio para não filtrar)
     * @param email - filtra pelo email (vazio para não filtrar)
     */
    public Page<UsuarioDto> consultarDto(
        PageRequest pageRequest,
        Long id,
        String nome,
        String email) {

        boolean consultaComFiltro =
            id != null || !nome.isBlank() || !email.isBlank();

        return consultaComFiltro ?
            usuarioModelRepository.consultarAtivosDto(pageRequest, id, nome, email) :
            usuarioModelRepository.consultarAtivosDto(pageRequest);
    }


    /** Retorna uma listagem simples de usuários (como Dto), sem filtragem.
     * @param pageRequest - Objeto com parâmetros de paginação e ordenação
     */
    public List<UsuarioDto> listarDto(PageRequest pageRequest) {
        return usuarioModelRepository.listarAtivosDto(pageRequest);
    }


    /**
     * Retorna uma listagem ordenada de usuários ativos cujo nome atende ao termo fornecido.
     * Adequado para processar requests estilo autocompletar.
     * @param nome - A parte do nome usada para refinar a busca
     */
    public List<UsuarioMinimoDto> buscarDto(String nome) {
        List<UsuarioMinimoDto> itens = usuarioModelRepository.buscarPorNome(nome);

        if (itens.size() > MAX_RESULTADOS_BUSCA)
            throw new RuntimeExceptionTratado("A busca resultou em um grande número de itens. Refine-a um pouco mais.");

        return itens;
    }


    /**
     * Retorna o usuário correspondente ao id fornecido, ou null caso não exista.
     * @param id - O identificador do registro no BD.
     */
    public Usuario obter(Long id) {
        return usuarioModelRepository.findById(id).orElse(null);
    }

}
