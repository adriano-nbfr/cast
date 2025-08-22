package br.mp.mpf.cast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.mp.mpf.cast.dto.comum.InformacaoSistemaDto;
import br.mp.mpf.cast.service.InformacaoSistemaService;


@RestController
public class InformacaoSistemaController {

    @Autowired
    InformacaoSistemaService indInformacaoSistemaService;

    @GetMapping("/api/public/informacao-sistema")
    public ResponseEntity<InformacaoSistemaDto> informacaoSistema() {
        return ResponseEntity.ok(indInformacaoSistemaService.obterInformacaoSistema());
    }

}
