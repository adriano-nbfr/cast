package br.mp.mpf.cast.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import br.mp.mpf.cast.dto.comum.AcaoLoteDto;
import br.mp.mpf.cast.dto.comum.EntidadeGenericaDto;


/** Classe base abstrata com métodos úteis para tratar requisições dos Rest Controllers que herdarem dela */
public abstract class RecursoRestBaseController {

    protected final String PARAMETRO_PAGE = "page";
    protected final String PARAMETRO_SIZE = "size";
    protected final String PARAMETRO_SORT = "sort";
    protected final String PARAMETRO_PAGINACAO_EMBUTIDA = "paginacaoEmbutida";


    /**
     * Retorna um objeto PageRequest a partir de valores de página, tamanho e ordenação.
     * @param page - O número da página.
     * @param size - O número máximmo de elementos exibidos por página
     * @param sort - A ordenação desejada (campo, asc/desc)
     */
    protected PageRequest formatarPageRequestConsulta(Integer page, Integer size, String sort) {
        String[] sortParts = sort.split(",");
        String sortCampo = sortParts.length > 0 ? sortParts[0].trim() : "";
        String sortDir = sortParts.length > 1 ? sortParts[1].trim() : "asc";

        Sort order = sortCampo.isEmpty() ?
            Sort.unsorted() :
            "desc".equals(sortDir) ?
                Sort.by(sortCampo).descending() :
                Sort.by(sortCampo).ascending();

        PageRequest pageRequest = PageRequest.of(page, size, order);
        return pageRequest;
    }


    /**
     * Retorna um objeto PageRequest a partir de parâmetros extraídos de um mapa obtido da url da requisição.
     * @param requestParamsMap - O mapa de parâmetros contido na url da requisição.
     * @param sortDefault - A ordenação default, caso não seja especificada nos parâmetros.
     */
    protected PageRequest formatarPageRequestConsulta(Map<String, String> requestParamsMap, String sortDefault) {

        Integer page = 0;
        Integer size = 10;
        String sort = sortDefault;

        if (requestParamsMap != null) {
            String pageLido = requestParamsMap.getOrDefault(PARAMETRO_PAGE, "").trim();
            String sizeLido = requestParamsMap.getOrDefault(PARAMETRO_SIZE, "").trim();
            String sortLido = requestParamsMap.getOrDefault(PARAMETRO_SORT, "").trim();

            try {
                page = Math.max(Integer.parseInt(!pageLido.isEmpty() ? pageLido : page.toString()), 0);
            }
            catch (Exception e) {
            }

            try {
                size = Math.max(Integer.parseInt(!sizeLido.isEmpty() ? sizeLido : size.toString()), 1);
            }
            catch (Exception e) {
            }

            if (!sortLido.isEmpty())
                sort = sortLido;
        }

        return formatarPageRequestConsulta(page, size, sort);
    }


    /**
     * Retorna uma estrutura de página completa a partir de uma lista.
     * @param listagem - A lista de elementos que entrará como conteúdo da página.
     */
    protected <T> Page<T> listagemComoPagina(List<T> listagem) {
        return new PageImpl<T>(listagem);
    }


    /**
     * Indica, a partir do mapa de parâmetros obtido da url da requisição, se o resultado deve
     * ser embutido numa estrutura completa com informações de paginação.
     * @param requestParamsMap - O mapa de parâmetros obtido da requisição.
     */
    protected boolean requisitadaPaginacaoEmbutidaNoResultado(Map<String, String> requestParamsMap) {
        if (requestParamsMap != null) {
            String paginacaoEmbutida = requestParamsMap
                .getOrDefault(PARAMETRO_PAGINACAO_EMBUTIDA, "").trim().toLowerCase();

            return !"false".equals(paginacaoEmbutida);
        }

        return true;
    }


    protected Long tratarComoLong(String valorStr) {
        try {
            if (valorStr == null || valorStr.isBlank())
                return null;

            return Long.parseLong(valorStr);
        }
        catch (Exception e) {
            return null;
        }
    }


    protected Integer tratarComoInteger(String valorStr) {
        try {
            if (valorStr == null || valorStr.isBlank())
                return null;

            return Integer.parseInt(valorStr);
        }
        catch (Exception e) {
            return null;
        }
    }


    protected Boolean tratarComoBoolean(String valorStr) {
        try {
            if (valorStr == null || valorStr.isBlank())
                return null;

            return "true".equals(valorStr) ? Boolean.TRUE : Boolean.FALSE;
        }
        catch (Exception e) {
            return null;
        }
    }


    protected LocalDate tratarComoData(String valorStr) {
        try {
            if (valorStr == null || valorStr.isBlank())
                return null;

            return LocalDate.parse(valorStr);
        }
        catch (Exception e) {
            return null;
        }
    }



    /**
     * Retorna uma lista com os Ids dos objetos passados na definição de uma ação em lote.
     * @param acaoLote - um objeto contendo a definição da ação em lote.
     */
    protected <T> List<Long> obterIdsAcaoLote(AcaoLoteDto<T> acaoLote) {
        return acaoLote.objetos.stream()
            .map(o -> {
                if (o != null && EntidadeGenericaDto.class.isAssignableFrom(o.getClass()))
                    return ((EntidadeGenericaDto)o).getId();

                try {
                    return Long.parseLong(o.toString());
                }
                catch (Exception e) {
                    return null;
                }
            })
            .filter(o -> o != null)
            .collect(Collectors.toList());
    }

}
