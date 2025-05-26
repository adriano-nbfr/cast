import { Injectable } from '@angular/core';
import { DsDatasourceRest } from '@dsmpf/ngx-dsmpf/datasource';
import { DsRecursoRestService } from '@dsmpf/ngx-dsmpf/rest';
import { Observable } from 'rxjs';
import { Andamento } from '../shared/model/andamento';
import { Pedido } from '../shared/model/pedido';

@Injectable({
  providedIn: 'root'
})
export class PedidosService extends DsRecursoRestService<Pedido> {

  protected override endpointRecurso(): string {
    return 'pedidos';
  }


  obterNovoPedido(idServico: number): Observable<Pedido> {
    return this.obterRecursoAuxiliar(`pedidos/novo?idServico=${idServico}`);
  }


  suspenderPedido(pedido: Pedido): Observable<Pedido> {
    return this.enviarPatch(`${pedido.id}/suspender`, {}, {relativo: true});
  }


  registrarAndamento(pedido: Pedido, descricao: string, arquivos?: File[]): Observable<Andamento> {
    const requestBody = {
      andamentoPedido: {descricao: descricao},
      arquivos: arquivos ?? []
    };

    return this.enviarPost(`${pedido.id}/andamentos`, requestBody, {relativo: true});
  }


  obterDatasourceMeusPedidos() {
    return new DsDatasourceRest<Pedido>(`${this.endpointApi}/meusPedidos`, this.consultaStrategy);
  }


  obterAndamentosPedido(idPedido: number | string) {
    const endpoint = `${idPedido}/andamentos`;
    return this.obterRecursoAuxiliar(endpoint, {relativo: true});
  }

}
