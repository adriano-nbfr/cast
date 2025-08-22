package br.mp.mpf.cast.dto;

import java.time.ZonedDateTime;

import br.mp.mpf.cast.dto.comum.EntidadeGenericaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AndamentoPedidoDto extends EntidadeGenericaDto {

	private Long id;
    private UsuarioMinimoDto usuario;
    private String descricao;
    private ZonedDateTime dataRegistro;
    private Long idArquivoAnexo;
    private String nomeArquivoAnexo;


    // Construtor adicional, desnormalizado para permitir a criação diretamente da query jpql
    public AndamentoPedidoDto(
        Long id,
        String descricao,
        ZonedDateTime dataRegistro,
        Long idUsuario,
        String nomeUsuario,
        Long idArquivoAnexo,
        String nomeArquivoAnexo) {

        this.id = id;
        this.descricao = descricao;
        this.dataRegistro = dataRegistro;
        this.idArquivoAnexo = idArquivoAnexo;
        this.nomeArquivoAnexo = nomeArquivoAnexo;
        this.usuario = new UsuarioMinimoDto(idUsuario, nomeUsuario);
    }

}

