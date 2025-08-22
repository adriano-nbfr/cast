package br.mp.mpf.cast.controller.manutencao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.mp.mpf.cast.dto.ServicoDto;
import br.mp.mpf.cast.dto.ServicoMinimoDto;
import br.mp.mpf.cast.dto.comum.AcaoLoteDto;
import br.mp.mpf.cast.mapper.ServicoMapper;
import br.mp.mpf.cast.model.Servico;
import br.mp.mpf.cast.service.manutencao.CategoriaService;
import br.mp.mpf.cast.service.manutencao.ServicoService;


@RestController
@RequestMapping("/api/manutencao/servicos")
public class ServicoController extends RecursoRestBaseController {

    @Autowired private ServicoService servicoService;

    @Autowired private CategoriaService categoriaService;

    @Autowired private ServicoMapper mapper;


    /** O método consultar pode retornar uma Page ou List, dependendo de um parâmetro específico na requisição */
    @GetMapping
    public ResponseEntity<Object> consultar(@RequestParam Map<String, String> parametros) {
        PageRequest pageRequest = this.formatarPageRequestConsulta(parametros, "categoria.nome,asc");
        boolean embutirPaginacao = this.requisitadaPaginacaoEmbutidaNoResultado(parametros);

        Long id = this.tratarComoLong(parametros.get("id"));
        String nome = parametros.getOrDefault("nome", "");
        Boolean ativo = this.tratarComoBoolean(parametros.get("ativo"));
        Long idCategoria = this.tratarComoLong(parametros.get("idCategoria"));

        if (embutirPaginacao)
            return ResponseEntity.ok(servicoService.consultarDto(pageRequest, id, nome, ativo, idCategoria));

        return ResponseEntity.ok(servicoService.listarDto(pageRequest));
    }


    @GetMapping("buscar")
    public List<ServicoMinimoDto> buscar (
        @RequestParam(required=true) String termo,
        @RequestParam(required=false) Long idCategoria) {

        return servicoService.buscarDto(termo, idCategoria);
    }


    @GetMapping("novo")
    public ResponseEntity<ServicoDto> obterNovo() {
        return ResponseEntity.ok(this.mapper.paraDto(new Servico()));
    }


    @GetMapping("{id}")
    public ResponseEntity<ServicoDto> obter(@PathVariable Long id) {
        Servico servico = servicoService.obter(id);

        if (servico == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(this.mapper.paraDto(servico));
    }


    @PostMapping
    public ResponseEntity<ServicoDto> incluir(@RequestBody ServicoDto servicoDto) {
        Servico servico = servicoService.salvar(mapper.paraModelo(servicoDto));
        return ResponseEntity.ok(mapper.paraDto(servico));
    }


    @PutMapping("{id}")
    public ResponseEntity<ServicoDto> alterar(
        @PathVariable Long id,
        @RequestBody ServicoDto servicoDto) {

        Servico servico = servicoService.salvar(mapper.paraModelo(servicoDto));
        return ResponseEntity.ok(mapper.paraDto(servico));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Long> excluir(@PathVariable Long id) {
        Servico servico = servicoService.obter(id);

        if (servico == null)
            return ResponseEntity.notFound().build();

        servicoService.excluir(servico);
        return ResponseEntity.ok(id);
    }


    @PatchMapping
    public ResponseEntity<List<Long>> processarAcaoEmLote(@RequestBody AcaoLoteDto<Long> acaoLote) {
        List<Long> ids = this.obterIdsAcaoLote(acaoLote);
        List<Long> idsSucesso = null;

        if ("excluir".equalsIgnoreCase(acaoLote.acao)) {
            idsSucesso = servicoService.excluirEmLote(ids);
        }

        return ResponseEntity.ok(idsSucesso != null ? idsSucesso : new ArrayList<Long>());
    }



    /// Endpoints secundários sem relacionamento com um ID específico do recurso principal

    @GetMapping("listar/categorias")
    public ResponseEntity<List<CategoriaListagemDto>> listarCategoriasAtivas() {
        return ResponseEntity.ok(categoriaService.listarAtivosOrdenadoPorNomeDto());
    }


}
