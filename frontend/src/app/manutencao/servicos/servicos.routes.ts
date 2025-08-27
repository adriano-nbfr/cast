import { construirRotasCrudPadrao, DsCrudDef } from '@dsmpf/ngx-dsmpf/crud';
import { environment } from '../../../environment';
import { obterFuncaoResolveEndpoint } from '@dsmpf/ngx-dsmpf/rest';


const resolveListagemCategorias =
  obterFuncaoResolveEndpoint('/manutencao/servicos/listar/categorias');


const servicosCrudDef: DsCrudDef = {
  textos: {
    nomeSingular: 'Serviço',
    nomePlural: 'Serviços',
    artigo: 'o'
  },
  endpointRest: 'manutencao/servicos',
  papeisEdicao: [environment.papeis.PAPEL_GERENTE],
  resolve: {
    listagemCategorias: resolveListagemCategorias
  },
  componenteConsultaLoadFn: () => import('./servicos-consulta/servicos-consulta')
    .then(m => m.ServicosConsulta),
  componenteEdicaoLoadFn: () => import('./servicos-edicao/servicos-edicao')
    .then(m => m.ServicosEdicao)
};

export default construirRotasCrudPadrao(servicosCrudDef);
