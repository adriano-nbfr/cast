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
    // Os métodos padrão do DsRecursoRestService detectam automaticamente a presença de um File
    // no body e convertem o objeto em um FormData antes do envio, quando necessário.
    const requestBody = arquivos && arquivos.length
      ? { andamentoPedido: { descricao: descricao }, arquivos: arquivos } // arquivo presente
      : { descricao: descricao }; // sem arquivo, o requestBody é apenas o próprio andamento

    return this.enviarPost(`${pedido.id}/andamentos`, requestBody, {relativo: true});
  }

  suspenderPedido(pedido: Pedido): Observable<Pedido> {
    return this.enviarPatch(`${pedido.id}/suspender`, {}, {relativo: true});
  }

  fecharPedido(pedido: Pedido, feedback?: {avaliacao: number, texto: string}): Observable<Pedido> {

    return this.enviarPatch(`${pedido.id}/fechar`, feedback ?? {}, {relativo: true});
  }

}
