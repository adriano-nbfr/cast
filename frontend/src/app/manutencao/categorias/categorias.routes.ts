import { construirRotasCrudPadrao, DsCrudDef } from "@dsmpf/ngx-dsmpf/crud";
import { environment } from "../../../environment";

const categoriasConfig: DsCrudDef = {
  textos: {
    nomePlural: 'Categorias',
    nomeSingular: 'Categoria',
    artigo: 'a'
  },
  endpointRest: 'manutencao/categorias',
  componenteConsultaLoadFn: () => import('./categorias-consulta/categorias-consulta.component')
    .then(m => m.CategoriasConsultaComponent),
  componenteEdicaoLoadFn: () => import('./categorias-edicao/categorias-edicao.component')
    .then(m => m.CategoriasEdicaoComponent),
  papeisEdicao: [ environment.papeis.PAPEL_GERENTE ],

};

export default construirRotasCrudPadrao(categoriasConfig);

