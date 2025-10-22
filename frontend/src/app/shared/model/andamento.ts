import { DsRecursoRest } from "@dsmpf/ngx-dsmpf/rest";
import { Usuario } from "./usuario";

export interface Andamento extends DsRecursoRest {
  usuario: Usuario;
  descricao: string;
  dataRegistro: string;
  idArquivoAnexo?: number;
  nomeArquivoAnexo: string;
}
