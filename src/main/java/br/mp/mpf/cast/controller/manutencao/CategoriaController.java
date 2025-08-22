package br.mp.mpf.cast.controller.manutencao;

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
import br.mp.mpf.cast.dto.CategoriaDto;
import br.mp.mpf.cast.dto.CategoriaListagemDto;
import br.mp.mpf.cast.dto.CategoriaMinimoDto;
import br.mp.mpf.cast.dto.ServicoDto;
import br.mp.mpf.cast.dto.ServicoListagemDto;
import br.mp.mpf.cast.dto.ServicoMinimoDto;
import br.mp.mpf.cast.dto.comum.AcaoLoteDto;
import br.mp.mpf.cast.mapper.CategoriaMapper;
import br.mp.mpf.cast.mapper.ServicoMapper;
import br.mp.mpf.cast.model.Categoria;
import br.mp.mpf.cast.model.Servico;
import br.mp.mpf.cast.service.manutencao.CategoriaService;
import br.mp.mpf.cast.service.manutencao.ServicoService;


@RestController
@RequestMapping("/api/manutencao/categorias")
public class CategoriaController extends RecursoRestBaseController {

    @Autowired private CategoriaService categoriaService;

    @Autowired private CategoriaMapper mapper;

    @Autowired private ServicoService servicoService;

    @Autowired private ServicoMapper mapperServico;


    /** O método consultar pode retornar uma Page ou List, dependendo de um parâmetro específico na requisição */
    @GetMapping
    public ResponseEntity<Object> consultar(@RequestParam Map<String, String> parametros) {
        PageRequest pageRequest = this.formatarPageRequestConsulta(parametros, "nome,asc");
        boolean embutirPaginacao = this.requisitadaPaginacaoEmbutidaNoResultado(parametros);

        Long id = this.tratarComoLong(parametros.get("id"));
        String nome = parametros.getOrDefault("nome", "");
        Boolean ativo = this.tratarComoBoolean(parametros.get("ativo"));

        if (embutirPaginacao)
            return ResponseEntity.ok(categoriaService.consultarDto(pageRequest, id, nome, ativo));

        return ResponseEntity.ok(categoriaService.listarDto(pageRequest));
    }


    @GetMapping("listar")
    public ResponseEntity<List<CategoriaListagemDto>> listar() {
        return ResponseEntity.ok(categoriaService.listarAtivosOrdenadoPorNomeDto());
    }


    @GetMapping("buscar")
    public List<CategoriaMinimoDto> buscar (@RequestParam(required=true) String termo) {
        return categoriaService.buscarDto(termo);
    }


    @GetMapping("novo")
    public ResponseEntity<CategoriaDto> obterNovo() {
        return ResponseEntity.ok(this.mapper.paraDto(new Categoria()));
    }


    @GetMapping("{id}")
    public ResponseEntity<CategoriaDto> obter(@PathVariable Long id) {
        Categoria categoria = categoriaService.obter(id);

        if (categoria == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(this.mapper.paraDto(categoria));
    }


    @PostMapping
    public ResponseEntity<CategoriaDto> incluir(@RequestBody CategoriaDto categoriaDto) {
        Categoria categoria = categoriaService.salvar(mapper.paraModelo(categoriaDto));
        return ResponseEntity.ok(mapper.paraDto(categoria));
    }


    @PutMapping("{id}")
    public ResponseEntity<CategoriaDto> alterar(
        @PathVariable Long id,
        @RequestBody CategoriaDto categoriaDto) {

        Categoria categoria = categoriaService.salvar(mapper.paraModelo(categoriaDto));
        return ResponseEntity.ok(mapper.paraDto(categoria));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Long> excluir(@PathVariable Long id) {
        Categoria categoria = categoriaService.obter(id);

        if (categoria == null)
            return ResponseEntity.notFound().build();

        categoriaService.excluir(categoria);
        return ResponseEntity.ok(id);
    }


    @PatchMapping
    public ResponseEntity<List<Long>> processarAcaoEmLote(@RequestBody AcaoLoteDto<Long> acaoLote) {
        List<Long> ids = this.obterIdsAcaoLote(acaoLote);
        List<Long> idsSucesso = null;

        if ("excluir".equalsIgnoreCase(acaoLote.acao)) {
            idsSucesso = categoriaService.excluirEmLote(ids);
        }

        return ResponseEntity.ok(idsSucesso != null ? idsSucesso : new ArrayList<Long>());
    }


    /// Endpoints secundários de relacionamento com Servico

    @GetMapping("{id}/servicos")
    public ResponseEntity<Page<ServicoListagemDto>> consultarServicosVinculados(
        @PathVariable Long id,
        @RequestParam Map<String, String> parametros) {

        PageRequest pageRequest = this.formatarPageRequestConsulta(parametros, "nome,asc");
        return ResponseEntity.ok(servicoService.consultarDto(pageRequest, null, "", null, id));
    }


    @GetMapping("{id}/servicos/listar")
    public ResponseEntity<List<ServicoMinimoDto>> listarServicosVinculados(@PathVariable Long id) {
        return ResponseEntity.ok(servicoService.listarAtivosPorCategoria(id));
    }


    @PostMapping("{id}/servicos")
    public ResponseEntity<ServicoDto> incluirServicoVinculado(@RequestBody ServicoDto servicoDto) {
        Servico servico = servicoService.salvar(mapperServico.paraModelo(servicoDto));
        return ResponseEntity.ok(mapperServico.paraDto(servico));
    }


    @PutMapping("{id}/servicos/{idServico}")
    public ResponseEntity<ServicoDto> alterarServicoVinculado(
        @PathVariable Long idServico,
        @RequestBody ServicoDto servicoDto) {

        Servico servico = servicoService.salvar(mapperServico.paraModelo(servicoDto));
        return ResponseEntity.ok(mapperServico.paraDto(servico));
    }


    @DeleteMapping("{id}/servicos/{idServico}")
    public ResponseEntity<Long> excluirServicoVinculado(@PathVariable Long idServico) {
        Servico servico = servicoService.obter(idServico);

        if (servico == null)
            return ResponseEntity.notFound().build();

        servicoService.excluir(servico);
        return ResponseEntity.ok(idServico);
    }

}
