package br.mp.mpf.cast.controller.manutencao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.mp.mpf.cast.controller.RecursoRestBaseController;
import br.mp.mpf.cast.dto.CategoriaListagemDto;
import br.mp.mpf.cast.dto.GrupoAtendimentoDto;
import br.mp.mpf.cast.dto.GrupoAtendimentoMinimoDto;
import br.mp.mpf.cast.dto.ItemGrupoAtendimentoDto;
import br.mp.mpf.cast.dto.ServicoMinimoDto;
import br.mp.mpf.cast.dto.UsuarioGrupoAtendimentoDto;
import br.mp.mpf.cast.dto.UsuarioMinimoDto;
import br.mp.mpf.cast.dto.comum.AcaoLoteDto;
import br.mp.mpf.cast.mapper.GrupoAtendimentoMapper;
import br.mp.mpf.cast.mapper.ItemGrupoAtendimentoMapper;
import br.mp.mpf.cast.mapper.UsuarioGrupoAtendimentoMapper;
import br.mp.mpf.cast.model.GrupoAtendimento;
import br.mp.mpf.cast.model.ItemGrupoAtendimento;
import br.mp.mpf.cast.model.UsuarioGrupoAtendimento;
import br.mp.mpf.cast.service.manutencao.CategoriaService;
import br.mp.mpf.cast.service.manutencao.GrupoAtendimentoService;
import br.mp.mpf.cast.service.manutencao.ItemGrupoAtendimentoService;
import br.mp.mpf.cast.service.manutencao.ServicoService;
import br.mp.mpf.cast.service.manutencao.UsuarioGrupoAtendimentoService;
import br.mp.mpf.cast.service.manutencao.UsuarioService;


@RestController
@RequestMapping("/api/manutencao/grupos-atendimento")
public class GrupoAtendimentoController extends RecursoRestBaseController {

    @Autowired private GrupoAtendimentoService grupoAtendimentoService;

    @Autowired private ItemGrupoAtendimentoService itemGrupoAtendimentoService;

    @Autowired private UsuarioGrupoAtendimentoService usuarioGrupoAtendimentoService;

    @Autowired private CategoriaService categoriaService;

    @Autowired private ServicoService servicoService;

    @Autowired private UsuarioService usuarioService;

    @Autowired private GrupoAtendimentoMapper mapper;

    @Autowired private ItemGrupoAtendimentoMapper mapperItemGrupo;

    @Autowired private UsuarioGrupoAtendimentoMapper mapperUsuarioGrupo;


    /** O método consultar pode retornar uma Page ou List, dependendo de um parâmetro específico na requisição */
    @GetMapping
    public ResponseEntity<Object> consultar(@RequestParam Map<String, String> parametros) {
        PageRequest pageRequest = this.formatarPageRequestConsulta(parametros, "nome,asc");
        boolean embutirPaginacao = this.requisitadaPaginacaoEmbutidaNoResultado(parametros);

        Long id = this.tratarComoLong(parametros.get("id"));
        String nome = parametros.getOrDefault("nome", "");
        Boolean ativo = this.tratarComoBoolean(parametros.get("ativo"));
        Long idCategoria = this.tratarComoLong(parametros.get("idCategoria"));

        if (embutirPaginacao)
            return ResponseEntity.ok(grupoAtendimentoService.consultarDto(pageRequest, id, nome, ativo, idCategoria));

        return ResponseEntity.ok(grupoAtendimentoService.listarDto(pageRequest));
    }


    @GetMapping("buscar")
    public List<GrupoAtendimentoMinimoDto> buscar (
        @RequestParam(required=true) String termo,
        @RequestParam(required=false) Long idCategoria) {
        return grupoAtendimentoService.buscarDto(termo, idCategoria);
    }


    @GetMapping("novo")
    public ResponseEntity<GrupoAtendimentoDto> obterNovo() {
        return ResponseEntity.ok(this.mapper.paraDto(new GrupoAtendimento()));
    }


    @GetMapping("{id}")
    public ResponseEntity<GrupoAtendimentoDto> obter(@PathVariable Long id) {
        GrupoAtendimento grupoAtendimento = grupoAtendimentoService.obter(id);

        if (grupoAtendimento == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(this.mapper.paraDto(grupoAtendimento));
    }


    @PostMapping
    public ResponseEntity<GrupoAtendimentoDto> incluir(@RequestBody GrupoAtendimentoDto grupoAtendimentoDto) {
        GrupoAtendimento grupoAtendimento = grupoAtendimentoService.salvar(mapper.paraModelo(grupoAtendimentoDto));
        return ResponseEntity.ok(mapper.paraDto(grupoAtendimento));
    }


    @PutMapping("{id}")
    public ResponseEntity<GrupoAtendimentoDto> alterar(
        @PathVariable Long id,
        @RequestBody GrupoAtendimentoDto grupoAtendimentoDto) {

        GrupoAtendimento grupoAtendimento = grupoAtendimentoService.salvar(mapper.paraModelo(grupoAtendimentoDto));
        return ResponseEntity.ok(mapper.paraDto(grupoAtendimento));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Long> excluir(@PathVariable Long id) {
        GrupoAtendimento grupoAtendimento = grupoAtendimentoService.obter(id);

        if (grupoAtendimento == null)
            return ResponseEntity.notFound().build();

        grupoAtendimentoService.excluir(grupoAtendimento);
        return ResponseEntity.ok(id);
    }


    @PatchMapping
    public ResponseEntity<List<Long>> processarAcaoEmLote(@RequestBody AcaoLoteDto<Long> acaoLote) {
        List<Long> ids = this.obterIdsAcaoLote(acaoLote);
        List<Long> idsSucesso = null;

        if ("excluir".equalsIgnoreCase(acaoLote.acao)) {
            idsSucesso = grupoAtendimentoService.excluirEmLote(ids);
        }

        return ResponseEntity.ok(idsSucesso != null ? idsSucesso : new ArrayList<Long>());
    }



    /// Endpoints secundários sem relacionamento com um ID específico do recurso principal

    @GetMapping("listar/categorias")
    public ResponseEntity<List<CategoriaListagemDto>> listarCategoriasAtivas() {
        return ResponseEntity.ok(categoriaService.listarAtivosOrdenadoPorNomeDto());
    }



    /// Endpoints secundários do relacionamento com ItemGrupoAtendimento (Servico)

    @GetMapping("{id}/servicos/buscar")
    public List<ServicoMinimoDto> buscarServicos (
        @RequestParam(required=true) String termo,
        @RequestParam(required=false) Long idCategoria) {

        return servicoService.buscarDto(termo, idCategoria);
    }


    @GetMapping("{id}/servicos")
    public ResponseEntity<Page<ItemGrupoAtendimentoDto>> consultarServicosAssociados(
        @PathVariable Long id,
        @RequestParam Map<String, String> parametros) {

        PageRequest pageRequest = this.formatarPageRequestConsulta(parametros, "servico.nome,asc");

        return ResponseEntity.ok(
            itemGrupoAtendimentoService.consultarPorGrupoAtendimentoDto(pageRequest, id)
        );
    }


    @PostMapping("{id}/servicos")
    public ResponseEntity<ItemGrupoAtendimentoDto> associarServico(
        @RequestBody ItemGrupoAtendimentoDto itemGrupoAtendimentoDto) {

        ItemGrupoAtendimento item = itemGrupoAtendimentoService
            .salvar(mapperItemGrupo.paraModelo(itemGrupoAtendimentoDto));

        return ResponseEntity.ok(mapperItemGrupo.paraDto(item));
    }


    @DeleteMapping("{id}/servicos/{idItemGrupoAtendimento}")
    public ResponseEntity<Long> desassociarServico(@PathVariable Long idItemGrupoAtendimento) {
        ItemGrupoAtendimento item = itemGrupoAtendimentoService.obter(idItemGrupoAtendimento);

        if (item == null)
            return ResponseEntity.notFound().build();

        itemGrupoAtendimentoService.excluir(item);
        return ResponseEntity.ok(idItemGrupoAtendimento);
    }



    /// Endpoints secundários do relacionamento com UsuarioGrupoAtendimento (Usuario atendente)

    @GetMapping("{id}/atendentes/buscar")
    public List<UsuarioMinimoDto> buscarUsuariosAtivos (
        @RequestParam(required=true) String termo,
        @RequestParam(required=false) Long idGrupoAtendimento) {

        if (idGrupoAtendimento != null)
            return usuarioGrupoAtendimentoService.buscarDto(termo, idGrupoAtendimento);

        return usuarioService.buscarDto(termo);
    }


    @GetMapping("{id}/atendentes/verificar")
    public boolean verificarExistenciaAtendenteNoGrupo (
        @RequestParam(required=true) Long idUsuario,
        @RequestParam(required=true) Long idGrupoAtendimento) {

        return usuarioGrupoAtendimentoService
            .isUsuarioAtendendoGrupoAtendimento(idUsuario, idGrupoAtendimento);
    }


    @GetMapping("{id}/atendentes")
    public ResponseEntity<Page<UsuarioGrupoAtendimentoDto>> consultarUsuariosAtendentes(
        @PathVariable Long id,
        @RequestParam Map<String, String> parametros) {

        PageRequest pageRequest = this.formatarPageRequestConsulta(parametros, "usuario.nome,asc");

        return ResponseEntity.ok(
            usuarioGrupoAtendimentoService.consultarPorGrupoAtendimentoDto(pageRequest, id)
        );
    }


    @GetMapping("{id}/atendentes/listar")
    public ResponseEntity<List<UsuarioGrupoAtendimentoDto>> listarUsuariosAtendentes(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioGrupoAtendimentoService.listarAtivosPorGrupoAtendimentoDto(id));
    }


    @PostMapping("{id}/atendentes")
    public ResponseEntity<UsuarioGrupoAtendimentoDto> associarUsuarioAtendente(
        @RequestBody UsuarioGrupoAtendimentoDto usuarioGrupoAtendimentoDto) {

        UsuarioGrupoAtendimento usuarioAtendente = usuarioGrupoAtendimentoService
            .salvar(mapperUsuarioGrupo.paraModelo(usuarioGrupoAtendimentoDto));

        return ResponseEntity.ok(mapperUsuarioGrupo.paraDto(usuarioAtendente));
    }


    @PatchMapping("{id}/atendentes/{idUsuarioGrupoAtendimento}/finalizar")
    public ResponseEntity<LocalDate> finalizarUsuarioAtendente(
        @PathVariable Long idUsuarioGrupoAtendimento) {

        UsuarioGrupoAtendimento usuarioAtendente =
            usuarioGrupoAtendimentoService.obter(idUsuarioGrupoAtendimento);

        if (usuarioAtendente == null)
            return ResponseEntity.notFound().build();

        usuarioAtendente = usuarioGrupoAtendimentoService.finalizar(usuarioAtendente);
        return ResponseEntity.ok(usuarioAtendente.getDataFim());
    }


    @DeleteMapping("{id}/atendentes/{idUsuarioGrupoAtendimento}")
    public ResponseEntity<Long> excluirUsuarioAtendente(@PathVariable Long idUsuarioGrupoAtendimento) {
        UsuarioGrupoAtendimento usuarioAtendente =
            usuarioGrupoAtendimentoService.obter(idUsuarioGrupoAtendimento);

        if (usuarioAtendente == null)
            return ResponseEntity.notFound().build();

        usuarioGrupoAtendimentoService.excluir(usuarioAtendente);
        return ResponseEntity.ok(idUsuarioGrupoAtendimento);
    }

}
