import { DsRecursoRest } from "@dsmpf/ngx-dsmpf/rest";

export interface Categoria extends DsRecursoRest {
  nome: string;
  descricao?: string;
  ativo: boolean;
}
