import { DsRecursoRest } from "@dsmpf/ngx-dsmpf/rest";

export interface Categoria extends DsRecursoRest {
  id: number;
  nome: string;
  ativo: boolean;
}
