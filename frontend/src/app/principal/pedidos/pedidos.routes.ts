import { Routes } from "@angular/router";

export default [
  {
    path: 'meus-pedidos',
    title: 'Meus Pedidos',
    loadComponent: () => import('./meus-pedidos/meus-pedidos').then(m => m.MeusPedidos)
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'meus-pedidos'
  }
] as Routes;
