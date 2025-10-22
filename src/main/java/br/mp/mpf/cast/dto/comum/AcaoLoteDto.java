package br.mp.mpf.cast.dto.comum;

import java.util.List;

/**
 * Classe que representa uma operação que deverá ser executada em lote, sobre uma lista de objetos.
 */
public class AcaoLoteDto<T> {
    /** Um identificador para a operação a ser executada */
    public String acao;
    /** A lista com os objetos que serão manipulados pela operação */
    public List<T> objetos;
}
