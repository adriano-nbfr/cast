import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, Routes } from "@angular/router";
import { DsAppSeguranca } from "@dsmpf/ngx-dsmpf/seguranca";
import { environment } from "../../environment";
import { PedidosService } from "./pedidos.service";

export default [
  {
    path: 'gerenciador',
    title: 'Painel gerencial',
    canMatch: [
      () => inject(DsAppSeguranca).isUsuarioAutorizadoAssincrono(environment.papeis.PAPEL_ATENDENTE, true)
    ],
    loadComponent: () => import('./pedidos-gerenciador/pedidos-gerenciador.component')
      .then(m => m.PedidosGerenciadorComponent)
  },
  {
    path: 'novo',
    title: 'Novo pedido',
    resolve: {
      novoPedido: (activeRoute: ActivatedRouteSnapshot) =>
        inject(PedidosService).obterNovoPedido(activeRoute.queryParams['idServico'])
    },
    loadComponent: () => import('./novo-pedido/novo-pedido.component')
      .then(m => m.NovoPedidoComponent)
  },
  {
    path: ':idPedido',
    title: 'Pedido - Visualização',
    resolve: {
      pedido: (activeRoute: ActivatedRouteSnapshot) =>
        inject(PedidosService).obter(activeRoute.params['idPedido'])
    },
    loadComponent: () => import('./pedido/pedido.component')
      .then(m => m.PedidoComponent)
  }

] as Routes;
