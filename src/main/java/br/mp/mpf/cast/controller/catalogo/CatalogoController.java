package br.mp.mpf.cast.controller.catalogo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.mp.mpf.cast.controller.RecursoRestBaseController;
import br.mp.mpf.cast.dto.CategoriaListagemDto;
import br.mp.mpf.cast.dto.ServicoMinimoDto;
import br.mp.mpf.cast.exception.RuntimeExceptionTratado;
import br.mp.mpf.cast.mapper.CategoriaMapper;
import br.mp.mpf.cast.service.manutencao.CategoriaService;
import br.mp.mpf.cast.service.manutencao.ServicoService;


@RestController
@RequestMapping("/api/catalogo")
public class CatalogoController extends RecursoRestBaseController {

    @Autowired private CategoriaService categoriaService;

    @Autowired private ServicoService servicoService;

    @Autowired private CategoriaMapper categoriaMapper;


    @GetMapping("categorias")
    public ResponseEntity<List<CategoriaListagemDto>> listarCategorias(HttpServletRequest request) {
        return ResponseEntity.ok(categoriaService.listarAtivosOrdenadoPorNomeDto());
    }

    @GetMapping("categorias/{idCategoria}")
    public ResponseEntity<CategoriaListagemDto> obterCategoria(@PathVariable Long idCategoria) {
        return ResponseEntity.ok(categoriaMapper.paraDtoListagem(categoriaService.obter(idCategoria)));
    }

    @GetMapping("categorias/{idCategoria}/servicos")
    public ResponseEntity<List<ServicoMinimoDto>> listarServicosVinculados(@PathVariable Long idCategoria) {
        return ResponseEntity.ok(servicoService.listarAtivosPorCategoria(idCategoria));
    }


    @GetMapping("categorias/{idCategoria}/icone")
    public ResponseEntity<byte[]> obterArquivo(@PathVariable Long idCategoria) {
        ClassPathResource resource = new ClassPathResource("static/img/catalogo/categoria_" + idCategoria + ".png");

        if (!resource.exists() || !resource.isReadable())
            return ResponseEntity.notFound().build();

        try {
            InputStream inputStream = resource.getInputStream();
            byte[] bytes = inputStream.readAllBytes();

            return ResponseEntity.ok()
            .contentLength(bytes.length)
            .contentType(MediaType.IMAGE_PNG)
            .body(bytes);
        } catch (IOException e) {
            throw new RuntimeExceptionTratado("Não foi possível obter o arquivo do ícone da categoria.");
        }
    }

}
