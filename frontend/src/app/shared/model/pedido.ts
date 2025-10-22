import { DsRecursoRest } from "@dsmpf/ngx-dsmpf/rest";
import { GrupoAtendimento } from "./grupo-atendimento";
import { Servico } from "./servico";
import { Usuario } from "./usuario";

export interface Pedido extends DsRecursoRest {
  servico: Servico;
  grupoAtendimento?: GrupoAtendimento;
  usuarioAbertura: Usuario;
  usuarioSolicitante: Usuario;
  usuarioAtendente?: Usuario;
  usuarioFechamento?: Usuario;
  titulo: string;
  descricao: string;
  urgencia: number;
  status: string;
  dataAbertura: string;
  dataFechamento?: string;
}
