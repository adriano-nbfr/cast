package br.mp.mpf.cast.controller.manutencao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.mp.mpf.cast.controller.RecursoRestBaseController;
import br.mp.mpf.cast.dto.UsuarioDto;
import br.mp.mpf.cast.dto.UsuarioGrupoAtendimentoDto;
import br.mp.mpf.cast.dto.UsuarioMinimoDto;
import br.mp.mpf.cast.mapper.UsuarioMapper;
import br.mp.mpf.cast.model.Usuario;
import br.mp.mpf.cast.service.manutencao.UsuarioGrupoAtendimentoService;
import br.mp.mpf.cast.service.manutencao.UsuarioService;


@RestController
@RequestMapping("/api/manutencao/usuarios")
public class UsuarioController extends RecursoRestBaseController {

    @Autowired private UsuarioService usuarioService;

    @Autowired private UsuarioGrupoAtendimentoService usuarioGrupoAtendimentoService;

    @Autowired private UsuarioMapper mapper;


    /** O método consultar pode retornar uma Page ou List, dependendo de um parâmetro específico na requisição */
    @GetMapping
    public ResponseEntity<Object> consultar(@RequestParam Map<String, String> parametros) {
        PageRequest pageRequest = this.formatarPageRequestConsulta(parametros, "nome,asc");
        boolean embutirPaginacao = this.requisitadaPaginacaoEmbutidaNoResultado(parametros);

        Long id = this.tratarComoLong(parametros.get("id"));
        String nome = parametros.getOrDefault("nome", "");
        String email = parametros.getOrDefault("email", "");

        if (embutirPaginacao)
            return ResponseEntity.ok(usuarioService.consultarDto(pageRequest, id, nome, email));

        return ResponseEntity.ok(usuarioService.listarDto(pageRequest));
    }


    @GetMapping("buscar")
    public List<UsuarioMinimoDto> buscar (@RequestParam(required=true) String termo) {
        return usuarioService.buscarDto(termo);
    }


    @GetMapping("{id}")
    public ResponseEntity<UsuarioDto> get(@PathVariable Long id) {
        Usuario usuario = usuarioService.obter(id);

        if (usuario == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(this.mapper.paraDto(usuario));
    }



    /// Endpoints secundários de relacionamento com UsuarioGrupoAtendimento

    @GetMapping("{id}/grupos")
    public ResponseEntity<Page<UsuarioGrupoAtendimentoDto>> listarGruposAtuacaoAtiva(
        @PathVariable Long id,
        @RequestParam Map<String, String> parametros) {

        PageRequest pageRequest = this.formatarPageRequestConsulta(parametros, "grupoAtendimento.nome,asc");

        return ResponseEntity.ok(
            usuarioGrupoAtendimentoService.consultarAtivosPorGrupoOuUsuarioDto(pageRequest, null, id)
        );
    }

}
