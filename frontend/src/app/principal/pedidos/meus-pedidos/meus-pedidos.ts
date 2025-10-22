import { DatePipe } from '@angular/common';
import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DsConteudoImports } from '@dsmpf/ngx-dsmpf/conteudo';
import { DsCardImports } from '@dsmpf/ngx-dsmpf/conteudo/card';
import { DsPlaceholderComponent } from '@dsmpf/ngx-dsmpf/elementos/bloco-assincrono';
import { CorStatusPedidoPipe } from '../../../shared/pipes/cor-status-pedido.pipe';
import { StatusPedidoPipe } from '../../../shared/pipes/status-pedido.pipe';
import { UrgenciaPedidoPipe } from '../../../shared/pipes/urgencia-pedido.pipe';
import { PedidosApi } from '../pedidos-api';

@Component({
  selector: 'app-meus-pedidos',
  imports: [
    RouterLink,
    DsConteudoImports,
    DsCardImports,
    DsPlaceholderComponent,
    UrgenciaPedidoPipe,
    StatusPedidoPipe,
    CorStatusPedidoPipe,
    DatePipe
  ],
  templateUrl: './meus-pedidos.html',
  styleUrl: './meus-pedidos.scss'
})
export class MeusPedidos implements OnInit, OnDestroy {

  private pedidosApi = inject(PedidosApi);

  protected datasource = this.pedidosApi.obterDatasourceMeusPedidos();


  ngOnInit() {
    this.datasource.conectar();
  }


  ngOnDestroy() {
    this.datasource.desconectar();
  }

}
