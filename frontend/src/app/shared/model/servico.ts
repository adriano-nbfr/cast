import { DsRecursoRest } from "@dsmpf/ngx-dsmpf/rest";
import { Categoria } from "./categoria";

export interface Servico extends DsRecursoRest {
  nome: string;
  descricao: string;
  ativo: boolean;
  categoria: Categoria;
}
