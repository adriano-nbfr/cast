import { Component, inject } from '@angular/core';
import { PedidosApi } from '../pedidos-api';
import { DsConteudoImports } from '@dsmpf/ngx-dsmpf/conteudo';
import { DsCardImports } from '@dsmpf/ngx-dsmpf/conteudo/card';
import { RouterLink } from '@angular/router';
import { DatePipe } from '@angular/common';
import { DsPlaceholderComponent } from '@dsmpf/ngx-dsmpf/elementos/bloco-assincrono';
import { CorStatusPedidoPipe } from '../../../shared/pipes/cor-status-pedido.pipe';
import { StatusPedidoPipe } from '../../../shared/pipes/status-pedido.pipe';
import { UrgenciaPedidoPipe } from '../../../shared/pipes/urgencia-pedido.pipe';

@Component({
  selector: 'app-meus-pedidos',
  imports: [
    RouterLink,
    DatePipe,
    DsConteudoImports,
    DsCardImports,
    DsPlaceholderComponent,
    CorStatusPedidoPipe,
    StatusPedidoPipe,
    UrgenciaPedidoPipe
  ],
  templateUrl: './meus-pedidos.html',
  styleUrl: './meus-pedidos.scss'
})
export class MeusPedidos {

  private pedidosApi = inject(PedidosApi);

  protected datasource = this.pedidosApi.obterDatasourceMeusPedidos();


  ngOnInit() {
    this.datasource.conectar();
  }

  ngOnDestroy() {
    this.datasource.desconectar();
  }

}
