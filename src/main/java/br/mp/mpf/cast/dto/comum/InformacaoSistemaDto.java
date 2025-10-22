package br.mp.mpf.cast.dto.comum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class InformacaoSistemaDto {

    String dataSistema;
    String versaoSistema;
    String ambiente;
    String host;

}
