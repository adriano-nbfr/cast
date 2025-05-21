import { Injectable } from '@angular/core';
import { DsRecursoRestService } from '@dsmpf/ngx-dsmpf/rest';
import { Pedido } from '../shared/model/pedido';

@Injectable({
  providedIn: 'root'
})
export class PedidosService extends DsRecursoRestService<Pedido> {

  protected override endpointRecurso(): string {
    return 'pedidos';
  }

}
