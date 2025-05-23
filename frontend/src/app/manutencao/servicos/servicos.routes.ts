import { construirRotasCrudPadrao, DsCrudDef } from '@dsmpf/ngx-dsmpf/crud';
import { obterFuncaoResolveEndpoint } from '@dsmpf/ngx-dsmpf/rest';
import { environment } from '../../../environment';

const resolveListagemCategorias =
  obterFuncaoResolveEndpoint('/manutencao/servicos/listar/categorias');


const servicosConfig: DsCrudDef = {
  textos: {
    nomePlural: 'Serviços',
    nomeSingular: 'Serviço',
    artigo: 'o'
  },
  endpointRest: 'manutencao/servicos',
  componenteConsultaLoadFn: () => import('./servicos-consulta/servicos-consulta.component')
    .then(m => m.ServicosConsultaComponent),
  componenteEdicaoLoadFn: () => import('./servicos-edicao/servicos-edicao.component')
    .then(m => m.ServicosEdicaoComponent),
  papeisEdicao: [environment.papeis.PAPEL_GERENTE],
  obterNovoPelaApi: false,
  resolve: {
    listagemCategorias: resolveListagemCategorias
  }
};


export default construirRotasCrudPadrao(servicosConfig);
