import { DsRecursoRest } from "@dsmpf/ngx-dsmpf/rest";

export interface Categoria extends DsRecursoRest {
  nome: string;
  ativo: boolean;
}
