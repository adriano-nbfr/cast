package br.mp.mpf.cast.service.pedido;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.mp.mpf.cast.dto.AndamentoPedidoDto;
import br.mp.mpf.cast.dto.PedidoListagemDto;
import br.mp.mpf.cast.exception.RuntimeExceptionTratado;
import br.mp.mpf.cast.model.AndamentoPedido;
import br.mp.mpf.cast.model.ArquivoAndamento;
import br.mp.mpf.cast.model.GrupoAtendimento;
import br.mp.mpf.cast.model.Pedido;
import br.mp.mpf.cast.model.Servico;
import br.mp.mpf.cast.model.Usuario;
import br.mp.mpf.cast.model.tipos.AvaliacaoPedido;
import br.mp.mpf.cast.model.tipos.StatusPedido;
import br.mp.mpf.cast.model.tipos.UrgenciaPedido;
import br.mp.mpf.cast.repository.AndamentoPedidoRepository;
import br.mp.mpf.cast.repository.ArquivoAndamentoRepository;
import br.mp.mpf.cast.repository.ItemGrupoAtendimentoRepository;
import br.mp.mpf.cast.repository.PedidoRepository;
import br.mp.mpf.cast.repository.ServicoRepository;
import br.mp.mpf.cast.repository.UsuarioRepository;

/**
 * pedido que concentra métodos e lógica de negócio relacionado à entidade Pedido
 */
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private AndamentoPedidoRepository andamentoPedidoRepository;

    @Autowired
    private ArquivoAndamentoRepository arquivoAndamentoRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ItemGrupoAtendimentoRepository itemGrupoAtendimentoRepository;


    /** Retorna uma estrutura completa de página contendo pedidos (como Dto), com filtragem parametrizada.
     * @param pageRequest - Objeto com parâmetros de paginação e ordenação
     * @param id - filtra pelo id (null para não filtrar)
     * @param texto - filtra pelo texto contido no título ou na descrição (vazio para não filtrar)
     * @param urgencia - filtra pela urgência (null para não filtrar)
     * @param status - filtra pelo status (null para não filtrar)
     * @param dataInicial - filtra pela data de abertura a partir da data informada (null para não filtrar)
     * @param dataFinal - filtra pela data de abertura até a data informada (null para não filtrar)
     * @param idPedido - filtra pelo pedido vinculado (null para não filtrar)
     * @param idGrupoAtendimento - filtra pelo grupo de atendimento associado (null para não filtrar)
     * @param idUsuarioSolicitante - filtra pelo usuário solicitante (null para não filtrar)
     * @param idUsuarioAtendente - filtra pelo usuário atendente (null para não filtrar)
     */
    public Page<PedidoListagemDto> consultarDto(
        PageRequest pageRequest,
        Long id,
        String texto,
        UrgenciaPedido urgencia,
        StatusPedido status,
        LocalDate dataInicial,
        LocalDate dataFinal,
        Long idPedido,
        Long idGrupoAtendimento,
        Long idUsuarioSolicitante,
        Long idUsuarioAtendente) {

        String statusStr = status != null ? status.name() : null;

        return pedidoRepository.consultarDto(
            pageRequest, id, texto, urgencia, statusStr, dataInicial, dataFinal,
            idPedido, idGrupoAtendimento, idUsuarioSolicitante, idUsuarioAtendente);
    }


    /** Retorna uma listagem simples contendo pedidos (como Dto) ordenados por urgência e data de abertura,
     * com filtragem parametrizada.
     * @param id - filtra pelo id (null para não filtrar)
     * @param texto - filtra pelo texto contido no título ou na descrição (vazio para não filtrar)
     * @param urgencia - filtra pela urgência (null para não filtrar)
     * @param status - filtra pelo status (null para não filtrar)
     * @param dataInicial - filtra pela data de abertura a partir da data informada (null para não filtrar)
     * @param dataFinal - filtra pela data de abertura até a data informada (null para não filtrar)
     * @param idPedido - filtra pelo pedido vinculado (null para não filtrar)
     * @param idGrupoAtendimento - filtra pelo grupo de atendimento associado (null para não filtrar)
     * @param idUsuarioSolicitante - filtra pelo usuário solicitante (null para não filtrar)
     * @param idUsuarioAtendente - filtra pelo usuário atendente (null para não filtrar)
     */
    public List<PedidoListagemDto> listarOrdenadoPorUrgenciaDto(
        Long id,
        String texto,
        UrgenciaPedido urgencia,
        StatusPedido status,
        LocalDate dataInicial,
        LocalDate dataFinal,
        Long idPedido,
        Long idGrupoAtendimento,
        Long idUsuarioSolicitante,
        Long idUsuarioAtendente) {

        String statusStr = status != null ? status.name() : null;

        return pedidoRepository.listarDtoOrdenadoPorUrgencia(
            id, texto, urgencia, statusStr, dataInicial, dataFinal,
            idPedido, idGrupoAtendimento, idUsuarioSolicitante, idUsuarioAtendente);
    }


    /** Retorna uma listagem simples contendo pedidos (como Dto) ordenados por data de abertura,
     * com filtragem parametrizada.
     * @param id - filtra pelo id (null para não filtrar)
     * @param texto - filtra pelo texto contido no título ou na descrição (vazio para não filtrar)
     * @param urgencia - filtra pela urgência (null para não filtrar)
     * @param status - filtra pelo status (null para não filtrar)
     * @param dataInicial - filtra pela data de abertura a partir da data informada (null para não filtrar)
     * @param dataFinal - filtra pela data de abertura até a data informada (null para não filtrar)
     * @param idPedido - filtra pelo pedido vinculado (null para não filtrar)
     * @param idGrupoAtendimento - filtra pelo grupo de atendimento associado (null para não filtrar)
     * @param idUsuarioSolicitante - filtra pelo usuário solicitante (null para não filtrar)
     * @param idUsuarioAtendente - filtra pelo usuário atendente (null para não filtrar)
     */
    public List<PedidoListagemDto> listarOrdenadoPorDataAberturaDto(
        Long id,
        String texto,
        UrgenciaPedido urgencia,
        StatusPedido status,
        LocalDate dataInicial,
        LocalDate dataFinal,
        Long idPedido,
        Long idGrupoAtendimento,
        Long idUsuarioSolicitante,
        Long idUsuarioAtendente) {

        String statusStr = status != null ? status.name() : null;

        return pedidoRepository.listarDtoOrdenadoPorDataAbertura(
            id, texto, urgencia, statusStr, dataInicial, dataFinal,
            idPedido, idGrupoAtendimento, idUsuarioSolicitante, idUsuarioAtendente);
    }


    /**
     * Retorna o pedido correspondente ao id fornecido, ou null caso não exista.
     * @param id - O identificador do registro no BD.
     */
    public Pedido obterNovo(Long idServico) {
        Servico servico = this.servicoRepository.findById(idServico).orElse(null);
        if (servico == null)
            throw new RuntimeExceptionTratado("Parâmetros inválidos.");

        // Para efeito de simulação, o usuário fake autenticado será aquele com id = 1
        Usuario usuario = usuarioRepository.findById(1L).orElse(null);

        Pedido novo = new Pedido();
        novo.setServico(servico);
        novo.setUsuarioSolicitante(usuario);
        novo.setUrgencia(UrgenciaPedido.MEDIA);
        return novo;
    }


    /**
     * Retorna o pedido correspondente ao id fornecido, ou null caso não exista.
     * @param id - O identificador do registro no BD.
     */
    public Pedido obter(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        return pedido;
    }


    /**
     * Persiste um pedido no BD, retornando o objeto atualizado.
     * @param pedido - O objeto a ser persistido.
     */
    public Pedido salvar(Pedido pedido) {
        if (pedido.getId() == null) {
            pedido.setStatus(StatusPedido.C);
        }

        if (pedido.getDataAbertura() == null)
            pedido.setDataAbertura(ZonedDateTime.now());

        if (pedido.getUsuarioAbertura() == null) {
            // Para efeito de simulação, o usuário fake autenticado será aquele com id = 1
            Usuario usuario = usuarioRepository.findById(1L).orElse(null);
            pedido.setUsuarioAbertura(usuario);
        }

        // Se o solicitante não veio definido, usa o próprio usuário de abertura
        if (pedido.getUsuarioSolicitante() == null)
            pedido.setUsuarioSolicitante(pedido.getUsuarioAbertura());

        // Tenta associar um grupo de atendimento automaticamente dentre os vinculados ao serviço
        if (pedido.getGrupoAtendimento() == null) {
            List<GrupoAtendimento> gruposServico = this.itemGrupoAtendimentoRepository
                .listarGruposPeloServico(pedido.getServico().getId());

            if (gruposServico.size() > 0)
                pedido.setGrupoAtendimento(gruposServico.get(0));
        }

        pedido = pedidoRepository.save(pedido);

        if (pedido.getGrupoAtendimento() != null) {
            String texto = "Grupo atribuído: " + pedido.getGrupoAtendimento().getNome();
            this.registrarAndamento(pedido, texto);
        }

        return pedido;
    }


    /**
     * Coloca um pedido em espera, caso não esteja fechado.
     * @param pedido - O objeto que deve ser excluído do BD.
     */
    public Pedido colocarEmEspera(Pedido pedido) {
        StatusPedido status = pedido.getStatus();
        if (status == StatusPedido.F)
            throw new RuntimeExceptionTratado("O pedido já está fechado e não pode ser colocado em espera.");

        if (status == StatusPedido.E)
            return pedido;

        registrarAndamento(pedido, "Status do pedido alterado para 'EM ESPERA'");
        pedido.setStatus(StatusPedido.E);
        return pedidoRepository.save(pedido);
    }


    /**
     * Fecha um pedido, caso já não esteja fechado.
     * @param pedido - O objeto que deve ser excluído do BD.
     */
    public Pedido fechar(Pedido pedido, AvaliacaoPedido avaliacao, String texto) {
        if (pedido.getStatus() == StatusPedido.F)
            throw new RuntimeExceptionTratado("O pedido já está fechado.");

        pedido.setStatus(StatusPedido.F);
        pedido.setAvaliacao(avaliacao);
        pedido.setTextoFechamento(texto);
        pedido = pedidoRepository.save(pedido);
        registrarAndamento(pedido, "Pedido FECHADO");
        return pedido;
    }


    /** Registra um andamento para um dado pedido.
     * @param texto - A descrição do andamento
     */
    public AndamentoPedido registrarAndamento(Pedido pedido, String texto) {
        if (pedido.getStatus().equals(StatusPedido.E)) {
            pedido.setStatus(StatusPedido.A);
            pedidoRepository.save(pedido);
        }

        AndamentoPedido andamento = new AndamentoPedido();
        andamento.setPedido(pedido);
        andamento.setDataRegistro(ZonedDateTime.now());
        andamento.setDescricao(texto);
        andamento.setUsuario(usuarioRepository.findById(1L).orElse(null));  // usuario fake
        return andamentoPedidoRepository.save(andamento);
    }


    /** Retorna uma estrutura completa de página contendo os andamentos de um pedido (como Dto)
     * @param pageRequest - Objeto com parâmetros de paginação e ordenação
     * @param idPedido - O id do pedido associado
     */
    public Page<AndamentoPedidoDto> consultarAndamentosPorPedidoDto(
        PageRequest pageRequest,
        Long idPedido) {

        return andamentoPedidoRepository.consultarPorPedidoDto(pageRequest, idPedido);
    }


    /** Retorna uma listagem simples contendo os andamentos de um pedido (como Dto) ordenados por data mais recente primeiro.
     * @param idPedido - O id do pedido associado
     */
    public List<AndamentoPedidoDto> listarAndamentosPorPedidoDto(Long idPedido) {
        return andamentoPedidoRepository.listarPorPedidoDto(idPedido);
    }


    public List<ArquivoAndamento> obterArquivosAndamento(
        Long idPedido,
        Long idAndamento,
        Long idArquivo) {

        AndamentoPedido andamento = andamentoPedidoRepository.findById(idAndamento).orElse(null);

        if (andamento == null)
            throw new RuntimeExceptionTratado("Andamento inexistente");

        return andamentoPedidoRepository.listarArquivosAndamento(idAndamento, idArquivo);
    }


    /**
     * Associa a lista de arquivos como andamento do pedido
     * @param andamento - O andamento cujos arquivos serão associados
     * @param arquivos - arquivos a serem associados
     */
    @Transactional(propagation =  Propagation.REQUIRED)
    public List<String> associarArquivosAndamentoPedido(
        AndamentoPedido andamento,
        List<MultipartFile> arquivos) {

        List<String> nomesArquivosSalvos = new ArrayList<String>();

        arquivos.forEach(arquivo -> {
            try {
                String nomeArquivo = salvarAnexoComoAndamento(andamento, arquivo);
                nomesArquivosSalvos.add(nomeArquivo);
            }
            catch (Exception e) {
                throw new RuntimeExceptionTratado(e);
            }
        });

        return nomesArquivosSalvos;
    }


    private String salvarAnexoComoAndamento(
        AndamentoPedido andamento,
        MultipartFile arquivo) throws IOException {

        String nomeArquivo = arquivo.getOriginalFilename().trim();
        String contentType = arquivo.getContentType();
        String texto = "Arquivo anexado: " + nomeArquivo;

        AndamentoPedido andamentoAnexo = this.registrarAndamento(andamento.getPedido(), texto);

        arquivoAndamentoRepository.save(
            new ArquivoAndamento(
                null,
                andamentoAnexo,
                nomeArquivo,
                arquivo.getBytes(),
                contentType));

        return nomeArquivo;
    }

}
