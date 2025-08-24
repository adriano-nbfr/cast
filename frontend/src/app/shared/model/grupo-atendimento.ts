import { DsRecursoRest } from "@dsmpf/ngx-dsmpf/rest";
import { Categoria } from "./categoria";

export interface GrupoAtendimento extends DsRecursoRest {
  nome: string;
  ativo: boolean;
  categoria: Categoria;
}
