import { construirRotasCrudPadrao, DsCrudDef } from "@dsmpf/ngx-dsmpf/crud";
import { environment } from "../../../environment";

const categoriasCrudDef: DsCrudDef = {
  textos: {
    nomeSingular: 'Categoria',
    nomePlural: 'Categorias',
    artigo: 'a'
  },
  endpointRest: 'manutencao/categorias',
  papeisEdicao: [environment.papeis.PAPEL_GERENTE],
  obterNovoPelaApi: true,
  componenteConsultaLoadFn: () => import('./categorias-consulta/categorias-consulta')
    .then(m => m.CategoriasConsulta),
  componenteEdicaoLoadFn: () => import('./categorias-edicao/categorias-edicao')
    .then(m => m.CategoriasEdicao)
};

export default construirRotasCrudPadrao(categoriasCrudDef);
