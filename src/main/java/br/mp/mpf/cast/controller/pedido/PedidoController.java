package br.mp.mpf.cast.controller.pedido;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.mp.mpf.cast.controller.RecursoRestBaseController;
import br.mp.mpf.cast.dto.AndamentoPedidoDto;
import br.mp.mpf.cast.dto.PedidoDto;
import br.mp.mpf.cast.dto.PedidoListagemDto;
import br.mp.mpf.cast.dto.UsuarioMinimoDto;
import br.mp.mpf.cast.exception.RuntimeExceptionTratado;
import br.mp.mpf.cast.mapper.AndamentoPedidoMapper;
import br.mp.mpf.cast.mapper.PedidoMapper;
import br.mp.mpf.cast.model.AndamentoPedido;
import br.mp.mpf.cast.model.ArquivoAndamento;
import br.mp.mpf.cast.model.GrupoAtendimento;
import br.mp.mpf.cast.model.Pedido;
import br.mp.mpf.cast.model.tipos.AvaliacaoPedido;
import br.mp.mpf.cast.model.tipos.StatusPedido;
import br.mp.mpf.cast.model.tipos.UrgenciaPedido;
import br.mp.mpf.cast.service.manutencao.UsuarioGrupoAtendimentoService;
import br.mp.mpf.cast.service.manutencao.UsuarioService;
import br.mp.mpf.cast.service.pedido.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController extends RecursoRestBaseController {

    @Autowired private PedidoService pedidoService;

    @Autowired private UsuarioGrupoAtendimentoService usuarioGrupoAtendimentoService;

     @Autowired private UsuarioService usuarioService;

    @Autowired private PedidoMapper mapper;

    @Autowired private AndamentoPedidoMapper andamentoPedidoMapper;


    /** O método consultar pode retornar uma Page ou List, dependendo de um parâmetro específico na requisição */
    @GetMapping
    public ResponseEntity<Object> consultar(
        @RequestParam Map<String, String> parametros) {
        PageRequest pageRequest = this.formatarPageRequestConsulta(parametros, "dataAbertura,desc");
        boolean embutirPaginacao = this.requisitadaPaginacaoEmbutidaNoResultado(parametros);

        Long id = this.tratarComoLong(parametros.get("id"));
        String texto = parametros.getOrDefault("texto", "");
        UrgenciaPedido urgencia = UrgenciaPedido.getByValor(this.tratarComoInteger(parametros.get("urgencia")));
        StatusPedido status = parametros.get("status") != null ? StatusPedido.valueOf(parametros.get("status")) : null;
        LocalDate dataInicial = this.tratarComoData(parametros.get("dataInicial"));
        LocalDate dataFinal = this.tratarComoData(parametros.get("dataFinal"));
        Long idServico = this.tratarComoLong(parametros.get("idServico"));
        Long idGrupoAtendimento = this.tratarComoLong(parametros.get("idGrupoAtendimento"));
        Long idUsuarioSolicitante = this.tratarComoLong(parametros.get("idUsuarioSolicitante"));
        Long idUsuarioAtendente = this.tratarComoLong(parametros.get("idUsuarioAtendente"));

        if (embutirPaginacao)
            return ResponseEntity.ok(pedidoService.consultarDto(
                pageRequest, id, texto, urgencia, status, dataInicial, dataFinal,
                idServico, idGrupoAtendimento, idUsuarioSolicitante, idUsuarioAtendente
            ));

        return ResponseEntity.ok(pedidoService.listarOrdenadoPorUrgenciaDto(
            id, texto, urgencia, status, dataInicial, dataFinal,
            idServico, idGrupoAtendimento, idUsuarioSolicitante, idUsuarioAtendente
        ));
    }


    @GetMapping("/ativosOrdenadosPorUrgencia")
    public ResponseEntity<List<PedidoListagemDto>> listarAtivosPorUrgencia(
        @RequestParam Map<String, String> parametros) {

        Long id = this.tratarComoLong(parametros.get("id"));
        String texto = parametros.getOrDefault("texto", "");
        UrgenciaPedido urgencia = UrgenciaPedido.getByValor(this.tratarComoInteger(parametros.get("urgencia")));
        StatusPedido status = parametros.get("status") != null ? StatusPedido.valueOf(parametros.get("status")) : null;
        LocalDate dataInicial = this.tratarComoData(parametros.get("dataInicial"));
        LocalDate dataFinal = this.tratarComoData(parametros.get("dataFinal"));
        Long idServico = this.tratarComoLong(parametros.get("idServico"));
        Long idGrupoAtendimento = this.tratarComoLong(parametros.get("idGrupoAtendimento"));
        Long idUsuarioSolicitante = this.tratarComoLong(parametros.get("idUsuarioSolicitante"));
        Long idUsuarioAtendente = this.tratarComoLong(parametros.get("idUsuarioAtendente"));

        return ResponseEntity.ok(pedidoService.listarOrdenadoPorUrgenciaDto(
            id, texto, urgencia, status, dataInicial, dataFinal,
            idServico, idGrupoAtendimento, idUsuarioSolicitante, idUsuarioAtendente
        ));
    }


    @GetMapping("novo")
    public ResponseEntity<PedidoDto> obterNovo(@RequestParam(required=true) Long idServico) {
        return ResponseEntity.ok(mapper.paraDto(this.pedidoService.obterNovo(idServico)));
    }


    @GetMapping("{id}")
    public ResponseEntity<PedidoDto> obter(@PathVariable Long id) {
        Pedido pedido = pedidoService.obter(id);

        if (pedido == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(this.mapper.paraDto(pedido));
    }


    @PostMapping
    public ResponseEntity<PedidoDto> incluir(@RequestBody PedidoDto pedidoDto) {
        Pedido pedido = this.pedidoService.salvar(mapper.paraModelo(pedidoDto));
        return ResponseEntity.ok(mapper.paraDto(pedido));
    }


    @PutMapping("{id}")
    public ResponseEntity<PedidoDto> alterar(
        @PathVariable Long id,
        @RequestBody PedidoDto pedidoDto) {

        Pedido pedido = this.pedidoService.salvar(mapper.paraModelo(pedidoDto));
        return ResponseEntity.ok(mapper.paraDto(pedido));
    }


    @PatchMapping("{id}/suspender")
    public ResponseEntity<PedidoDto> suspender(@PathVariable Long id) {

        Pedido pedido = pedidoService.obter(id);

        if (pedido == null)
            return ResponseEntity.notFound().build();

        pedido = this.pedidoService.colocarEmEspera(pedido);
        return ResponseEntity.ok(mapper.paraDto(pedido));
    }


    @PatchMapping("{id}/fechar")
    public ResponseEntity<PedidoDto> fechar(
        @PathVariable Long id,
        @RequestParam(required=false) AvaliacaoPedido avaliacao,
        @RequestParam(required=false) String texto
        ) {

        Pedido pedido = pedidoService.obter(id);

        if (pedido == null)
            return ResponseEntity.notFound().build();

        pedido = this.pedidoService.fechar(pedido, avaliacao, texto);
        return ResponseEntity.ok(mapper.paraDto(pedido));
    }


    /// Endpoints secundários auxiliares

    @GetMapping("/buscar/usuarios")
    public List<UsuarioMinimoDto> buscarUsuarios (@RequestParam(required=true) String termo) {
        return usuarioService.buscarDto(termo);
    }


    /// Endpoints secundários do relacionamento com AndamentoPedido (Usuario atendente)

    @GetMapping("{id}/atendentes/buscar")
    public List<UsuarioMinimoDto> buscarAtendentes (
        @PathVariable Long id,
        @RequestParam(required=true) String termo,
        @RequestParam(required=false) Long idGrupoAtendimento) {

        if (idGrupoAtendimento == null) {
            GrupoAtendimento grupo = pedidoService.obter(id).getGrupoAtendimento();
            if (grupo != null)
                idGrupoAtendimento = grupo.getId();
        }

        if (idGrupoAtendimento != null)
            return usuarioGrupoAtendimentoService.buscarDto(termo, idGrupoAtendimento);

        return usuarioService.buscarDto(termo);
    }


    @GetMapping("{id}/atendentes/listar")
    public ResponseEntity<List<UsuarioMinimoDto>> listarAtendentes(
        @PathVariable Long id,
        @RequestParam(required=false) Long idGrupoAtendimento) {

        if (idGrupoAtendimento == null) {
            GrupoAtendimento grupo = pedidoService.obter(id).getGrupoAtendimento();
            if (grupo != null)
                idGrupoAtendimento = grupo.getId();
        }

        return ResponseEntity.ok(
            usuarioGrupoAtendimentoService.listarAtivosPorGrupoAtendimentoDto(idGrupoAtendimento)
                .stream().map(item -> item.getUsuario()).collect(Collectors.toList())
        );
    }


    /// Endpoints secundários do relacionamento com AndamentoPedido

    @GetMapping("{id}/andamentos")
    public ResponseEntity<List<AndamentoPedidoDto>> listarAndamentos(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.listarAndamentosPorPedidoDto(id));
    }


    @PostMapping(path="{id}/andamentos", consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<AndamentoPedidoDto> registrarAndamento(
        @PathVariable Long id,
        @RequestPart(name = "andamentoPedido", required = true) AndamentoPedidoDto andamentoPedidoDto,
        @RequestPart List<MultipartFile> arquivos) {

        Pedido pedido = pedidoService.obter(id);

        if (pedido == null)
            return ResponseEntity.notFound().build();

        AndamentoPedido andamento = pedidoService
            .registrarAndamento(pedido, andamentoPedidoDto.getDescricao());

        if (arquivos.size() > 0)
            pedidoService.associarArquivosAndamentoPedido(andamento, arquivos);

        return ResponseEntity.ok(andamentoPedidoMapper.paraDto(andamento));
    }


    // Obtém o conteúdo do arquivo de postagem
    @GetMapping("{id}/andamentos/{idAndamento}/arquivos/{idArquivo}")
    public ResponseEntity<byte[]> obterArquivoAndamento(
        @PathVariable Long id,
        @PathVariable Long idAndamento,
        @PathVariable Long idArquivo) {

        Pedido pedido = pedidoService.obter(id);

        if (pedido == null)
            return ResponseEntity.notFound().build();

        try {
            List<ArquivoAndamento> arquivos = pedidoService
                .obterArquivosAndamento(id, idAndamento, idArquivo);

            ArquivoAndamento arquivo = arquivos.stream()
                .filter((ArquivoAndamento a) -> a.getId().equals(idArquivo))
                .findFirst().orElse(null);

            if (arquivo == null)
                throw new RuntimeExceptionTratado("Arquivo não encontrado");

            return ResponseEntity.ok()
            .contentType(MediaType.valueOf(arquivo.getContentType()))
            .body(arquivo.getArquivo());
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
