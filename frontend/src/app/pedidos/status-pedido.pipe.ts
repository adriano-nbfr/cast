import { Pipe, PipeTransform } from "@angular/core";

const StatusPedido: Record<string, string> = {
  'C': 'Cadastrado',
  'A': 'Atribuído',
  'E': 'Em espera',
  'F': 'Fechado'
};


@Pipe({
  name: 'statusPedido'
})
export class StatusPedidoPipe implements PipeTransform {

  transform(valor: string) {
    return StatusPedido[valor];
  }

}
