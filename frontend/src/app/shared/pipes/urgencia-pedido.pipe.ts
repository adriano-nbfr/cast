import { Pipe, PipeTransform } from "@angular/core";

const UrgenciaPedido: Record<number, string> = {
  0: 'Muito baixa',
  1: 'Baixa',
  2: 'Média',
  3: 'Alta',
  4: 'Muito alta',
};


/** Array com os valores possíveis para urgência de um pedido */
export const TiposUrgenciaPedido = Array.from(Object.keys(UrgenciaPedido)).map(v => parseInt(v));


@Pipe({
  name: 'urgenciaPedido'
})
export class UrgenciaPedidoPipe implements PipeTransform {

  transform(valor: number) {
    return UrgenciaPedido[valor];
  }

}
