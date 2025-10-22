import { DsParametrosAplicacao } from "@dsmpf/ngx-dsmpf/configuracao";
import { DsParametrosSeguranca } from "@dsmpf/ngx-dsmpf/seguranca";


const PAPEIS = {
  PAPEL_GERENTE: 'ATUACAO_OK',
  PAPEL_ATENDENTE: 'ATUACAO_OK',
  PAPEL_ADMIN: 'CAST_ADMIN'
};


const parametrosAplicacao: DsParametrosAplicacao = {
  nome: "CAST",
  sigla: "cast",
  logo: {
    src: "logo.svg",
  },
  basePath: "/cast",
  api: {
    raiz: "/api",
    informacaoSistema: "/api/public/informacao-sistema"
  }
};

// Parâmetros de autenticação/autorização específicos (definidos no back end)
const parametrosSeguranca: DsParametrosSeguranca = {
  papelAdmin: PAPEIS.PAPEL_ADMIN,
  api: {
    usuarioAutenticado: "/api/__seguranca/usuario",
    atuacoes: "/api/__seguranca/atuacoes-json",
    papeis: "/api/__seguranca/papeis",
    atuacaoCorrente: "/api/__seguranca/atuacoes/atual",
    atuacaoEscolha: "/api/__seguranca/atuacoes",
    logout: "logout"
  }
};

/** Parâmetros de ambiente usados na configuração da aplicação */
export const environment = {
  production: false,
  parametrosAplicacao: parametrosAplicacao,
  parametrosSeguranca: parametrosSeguranca,
  papeis: PAPEIS
};
