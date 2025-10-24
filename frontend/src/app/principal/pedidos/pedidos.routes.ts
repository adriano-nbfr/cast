import { inject } from "@angular/core";
import { Routes } from "@angular/router";
import { DsAppSeguranca } from "@dsmpf/ngx-dsmpf/seguranca";
import { environment } from "../../../environment";

export default [
  {
    path: 'meus-pedidos',
    title: 'Meus Pedidos',
    loadComponent: () => import('./meus-pedidos/meus-pedidos').then(m => m.MeusPedidos)
  },
  {
    path: 'painel-gerencial',
    title: 'Painel Gerencial',
    loadComponent: () => import('./painel-gerencial/painel-gerencial').then(m => m.PainelGerencial),
    canMatch: [
      () => inject(DsAppSeguranca).isUsuarioAutorizadoAssincrono(environment.papeis.PAPEL_ATENDENTE, true)
    ]
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'meus-pedidos'
  }
] as Routes;
