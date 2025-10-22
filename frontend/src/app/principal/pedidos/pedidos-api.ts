import { Injectable } from '@angular/core';
import { DsRecursoRestService } from '@dsmpf/ngx-dsmpf/rest';
import { Pedido } from '../../shared/model/pedido';
import { DsDatasourceRest } from '@dsmpf/ngx-dsmpf/datasource';

@Injectable({
  providedIn: 'root'
})
export class PedidosApi extends DsRecursoRestService<Pedido> {

  protected override endpointRecurso(): string {
    return 'pedidos';
  }

  obterDatasourceMeusPedidos() {
    return new DsDatasourceRest<Pedido>(`${this.endpointApi}/meusPedidos`, this.consultaStrategy);
  }

}
