package br.mp.mpf.cast.service.manutencao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.mp.mpf.cast.dto.UsuarioGrupoAtendimentoDto;
import br.mp.mpf.cast.dto.UsuarioMinimoDto;
import br.mp.mpf.cast.exception.RuntimeExceptionTratado;
import br.mp.mpf.cast.model.UsuarioGrupoAtendimento;
import br.mp.mpf.cast.repository.UsuarioGrupoAtendimentoRepository;

/**
 * Serviço que concentra métodos e lógica de negócio relacionado à entidade UsuarioGrupoAtendimento
 */
@Service
public class UsuarioGrupoAtendimentoService {

    private static final int MAX_RESULTADOS_BUSCA = 100;

    @Autowired private UsuarioGrupoAtendimentoRepository usuarioGrupoAtendimentoRepository;


    /** Retorna uma estrutura completa de página contendo associações de usuário com grupo de atendimento (como Dto)
     * para um grupo de atendimento específico.
     * @param pageRequest - Objeto com parâmetros de paginação e ordenação
     * @param idGrupoAtendimento - O id do grupo de atendimento associado
     */
    public Page<UsuarioGrupoAtendimentoDto> consultarPorGrupoAtendimentoDto(
        PageRequest pageRequest,
        Long idGrupoAtendimento) {

        return usuarioGrupoAtendimentoRepository.consultarPorGrupoAtendimentoDto(pageRequest, idGrupoAtendimento);
    }


    /** Retorna uma estrutura completa de página contendo associações de usuário com grupo de atendimento (como Dto)
     * para um usuário específico.
     * @param pageRequest - Objeto com parâmetros de paginação e ordenação
     * @param idUsuario - O id do usuário associado
     */
    public Page<UsuarioGrupoAtendimentoDto> consultarPorUsuarioDto(
        PageRequest pageRequest,
        Long idUsuario) {

        return usuarioGrupoAtendimentoRepository.consultarPorUsuarioDto(pageRequest, idUsuario);
    }


   /** Retorna uma estrutura completa de página contendo as associações ativas considerando a dataInicio e dataFim (como Dto),
     * podendo filtrar por usuário ou grupo de atendimento (ou ambos).
      @param pageRequest - Objeto com parâmetros de paginação e ordenação
     * @param idGrupoAtendimento - O id do grupo de atendimento (null para não filtrar)
     * @param idUsuario - O id do usuário (null para não filtrar)
     */
    public Page<UsuarioGrupoAtendimentoDto> consultarAtivosPorGrupoOuUsuarioDto(
        PageRequest pageRequest,
        Long idGrupoAtendimento,
        Long idUsuario) {

        if (idGrupoAtendimento == null && idUsuario == null)
            throw new RuntimeExceptionTratado("Parâmetros inválidos: usuário e grupo de atendimento não podem ser ambos nulos.");

        return usuarioGrupoAtendimentoRepository
            .consultarAtivosPorGrupoOuUsuarioDto(pageRequest, idGrupoAtendimento, idUsuario);
    }


    /** Retorna uma listagem simples de associações de usuário com grupo de atendimento (como Dto), sem filtragem.
     * @param pageRequest - Objeto com parâmetros de paginação e ordenação
     */
    public List<UsuarioGrupoAtendimentoDto> listarAtivosPorGrupoAtendimentoDto(Long idGrupoAtendimento) {
        return usuarioGrupoAtendimentoRepository.listarAtivosPorGrupoOuUsuarioDto(idGrupoAtendimento, null);
    }


    public boolean isUsuarioAtendendoGrupoAtendimento(Long idUsuario, Long idGrupoAtendimento) {
        if (idGrupoAtendimento == null || idUsuario == null)
            throw new RuntimeExceptionTratado("Parâmetros inválidos: usuário e grupo de atendimento devem ser informados.");

        return !usuarioGrupoAtendimentoRepository
            .listarAtivosPorGrupoOuUsuarioDto(idGrupoAtendimento, idUsuario).isEmpty();
    }


    /**
     * Retorna a associação de usuário com grupo de atendimento correspondente ao id fornecido, ou null caso não exista.
     * @param id - O identificador do registro no BD.
     */
    public UsuarioGrupoAtendimento obter(Long id) {
        return usuarioGrupoAtendimentoRepository.findById(id).orElse(null);
    }


    /**
     * Persiste uma associação de usuário com grupo de atendimento no BD, retornando o objeto atualizado.
     * @param usuarioGrupoAtendimento - O objeto a ser persistido.
     */
    public UsuarioGrupoAtendimento salvar(UsuarioGrupoAtendimento usuarioGrupoAtendimento) {
        if (usuarioGrupoAtendimento.getDataInicio() == null)
            usuarioGrupoAtendimento.setDataInicio(LocalDate.now());

        return usuarioGrupoAtendimentoRepository.save(usuarioGrupoAtendimento);
    }


    /**
     * Remove uma associação usuário com grupo de atendimento do BD, caso exista.
     * @param usuarioGrupoAtendimento - O objeto que deve ser excluído do BD.
     */
    public void excluir(UsuarioGrupoAtendimento usuarioGrupoAtendimento) {
        usuarioGrupoAtendimentoRepository.delete(usuarioGrupoAtendimento);
    }


    /**
     * Retorna uma listagem ordenada de associações de usuário com grupo de atendimento (como Dto)
     * cujo nome atende ao termo fornecido, filtrando opcionalmente pelo grupo de atendimento.
     * Adequado para processar requests estilo autocompletar.
     * @param termo - A parte do nome usada para refinar a busca
     * @param idGrupoAtendimento - filtra pelo id do grupo de atendimento (null para não filtrar)
     */
    public List<UsuarioMinimoDto> buscarDto(String termo, Long idGrupoAtendimento) {
        List<UsuarioGrupoAtendimentoDto> itens = usuarioGrupoAtendimentoRepository
            .buscarPorNome(termo, idGrupoAtendimento);

        if (itens.size() > MAX_RESULTADOS_BUSCA)
            throw new RuntimeExceptionTratado("A busca resultou em um grande número de itens. Refine-a um pouco mais.");

        return itens.stream().map(item -> item.getUsuario()).collect(Collectors.toList());
    }


    /**
     * Finaliza (dataFim = hoje) uma associação de usuário com grupo de atendimento, retornando o objeto atualizado.
     * @param usuarioGrupoAtendimento - O objeto a ser persistido.
     */
    public UsuarioGrupoAtendimento finalizar(UsuarioGrupoAtendimento usuarioGrupoAtendimento) {
        LocalDate hoje = LocalDate.now();

        if (hoje.isBefore(usuarioGrupoAtendimento.getDataInicio()))
            usuarioGrupoAtendimento.setDataInicio(hoje);

        usuarioGrupoAtendimento.setDataFim(hoje);
        return usuarioGrupoAtendimentoRepository.save(usuarioGrupoAtendimento);
    }

}
