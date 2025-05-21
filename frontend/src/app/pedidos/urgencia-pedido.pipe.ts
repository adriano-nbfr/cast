import { Pipe, PipeTransform } from "@angular/core";

const UrgenciaPedido: Record<number, string> = {
  0: 'Muito baixa',
  1: 'Baixa',
  2: 'Média',
  3: 'Alta',
  4: 'Muito alta',
};


@Pipe({
  name: 'urgenciaPedido'
})
export class UrgenciaPedidoPipe implements PipeTransform {

  transform(valor: number) {
    return UrgenciaPedido[valor];
  }

}