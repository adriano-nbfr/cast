import { Categoria } from "./categoria";

export interface Servico {
  id: number;
  nome: string;
  descricao: string;
  ativo: boolean;
  categoria: Categoria;
}
