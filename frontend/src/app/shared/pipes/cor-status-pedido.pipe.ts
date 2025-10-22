import { Pipe, PipeTransform } from "@angular/core";

const CorStatusPedido: Record<string, string> = {
  'C': '',
  'A': 'text-success',
  'E': 'text-warning',
  'F': 'text-danger'
};


@Pipe({
  name: 'corStatusPedido'
})
export class CorStatusPedidoPipe implements PipeTransform {

  transform(valor: string) {
    return CorStatusPedido[valor];
  }

}
