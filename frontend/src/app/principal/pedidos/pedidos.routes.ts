import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, Routes } from "@angular/router";
import { DsAppSeguranca } from "@dsmpf/ngx-dsmpf/seguranca";
import { environment } from "../../../environment";
import { PedidosApi } from "./pedidos-api";

export default [
  {
    path: 'meus-pedidos',
    title: 'Meus Pedidos',
    loadComponent: () => import('./meus-pedidos/meus-pedidos').then(m => m.MeusPedidos)
  },
  {
    path: 'painel-gerencial',
    title: 'Painel Gerencial',
    canMatch: [
      () => inject(DsAppSeguranca).isUsuarioAutorizadoAssincrono(environment.papeis.PAPEL_ATENDENTE)
    ],
    loadComponent: () => import('./painel-gerencial/painel-gerencial').then(m => m.PainelGerencial)
  },
  {
    path: 'novo',
    title: 'Novo pedido',
    resolve: {
      novoPedido: (activatedRoute: ActivatedRouteSnapshot) => {
        return inject(PedidosApi).obterNovoPedido(activatedRoute.queryParams['idServico'])
      }
    },
    loadComponent: () => import('./novo-pedido/novo-pedido').then(m => m.NovoPedido)
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'meus-pedidos'
  }
] as Routes;
