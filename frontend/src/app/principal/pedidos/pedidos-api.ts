import { Injectable } from '@angular/core';
import { DsDatasourceRest } from '@dsmpf/ngx-dsmpf/datasource';
import { DsRecursoRestService } from '@dsmpf/ngx-dsmpf/rest';
import { Observable } from 'rxjs';
import { Andamento } from '../../shared/model/andamento';
import { Pedido } from '../../shared/model/pedido';

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

  obterDatasourcePainelGerencial() {
    return new DsDatasourceRest<Pedido>(`${this.endpointApi}`, this.consultaStrategy);
  }

  obterNovoPedido(idServico: number): Observable<Pedido> {
    return this.obterRecursoAuxiliar(`pedidos/novo?idServico=${idServico}`);
  }

  obterAndamentosPedido(idPedido: number | string): Observable<Andamento[]> {
    const endpoint = `${idPedido}/andamentos`;
    return this.obterRecursoAuxiliar(endpoint, {relativo: true});
  }

  registrarAndamento(pedido: Pedido, descricao: string, arquivos?: File[]): Observable<Andamento> {
    const requestBody = arquivos && arquivos.length
      // andamento e arquivos serão automaticamente enviados como partes de um FormData (pela presença do File)
      ? { andamentoPedido: { descricao: descricao }, arquivos: arquivos }
      : { descricao: descricao }; // sem arquivo, o requestBody é apenas o próprio andamento

    return this.enviarPost(`${pedido.id}/andamentos`, requestBody, {relativo: true});
  }

  suspenderPedido(pedido: Pedido): Observable<Pedido> {
    return this.enviarPatch(`${pedido.id}/suspender`, {}, {relativo: true});
  }

}
