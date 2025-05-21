import { DsRecursoRest } from "@dsmpf/ngx-dsmpf/rest";

export interface Usuario extends DsRecursoRest {
  nome: string;
  email: string;
}
