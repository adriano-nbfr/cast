import { DatePipe } from '@angular/common';
import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { DsConteudoImports } from '@dsmpf/ngx-dsmpf/conteudo';
import { DsCardImports } from '@dsmpf/ngx-dsmpf/conteudo/card';
import { DsPlaceholderComponent } from '@dsmpf/ngx-dsmpf/elementos/bloco-assincrono';
import { DsBotaoComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { DsDatepickerDirective } from '@dsmpf/ngx-dsmpf/form/datepicker';
import { CorStatusPedidoPipe } from '../cor-status-pedido.pipe';
import { PedidosService } from '../pedidos.service';
import { StatusPedidoPipe, TiposStatusPedido } from '../status-pedido.pipe';
import { TiposUrgenciaPedido, UrgenciaPedidoPipe } from '../urgencia-pedido.pipe';

@Component({
  selector: 'app-meus-pedidos',
  imports: [
    RouterLink,
    FormsModule,
    DatePipe,
    DsConteudoImports,
    DsCardImports,
    DsFormImports,
    DsDatepickerDirective,
    DsBotaoComponent,
    DsPlaceholderComponent,
    UrgenciaPedidoPipe,
    StatusPedidoPipe,
    CorStatusPedidoPipe,
  ],
  templateUrl: './meus-pedidos.component.html',
  styleUrl: './meus-pedidos.component.scss'
})
export class MeusPedidosComponent implements OnInit, OnDestroy {

  private pedidosService = inject(PedidosService);

  protected datasource = this.pedidosService.obterDatasourceMeusPedidos();

  protected opcoesUrgencia = TiposUrgenciaPedido;

  protected opcoesStatus = TiposStatusPedido;


  ngOnInit() {
    this.datasource.conectar();
  }


  ngOnDestroy() {
    this.datasource.desconectar();
  }


  consultar(filtro: object) {
    this.datasource.filtro = filtro;
    this.datasource.atualizar();
  }

}
