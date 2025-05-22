import { ActivatedRouteSnapshot, Routes } from "@angular/router";
import { PedidosService } from "./pedidos.service";
import { inject } from "@angular/core";

export default [
  {
    path: 'gerenciador',
    title: 'Painel gerencial',
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
  }

] as Routes;
