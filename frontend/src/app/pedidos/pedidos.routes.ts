import { Routes } from "@angular/router";

export default [
  {
    path: 'gerenciador',
    title: 'Painel gerencial',
    loadComponent: () => import('./pedidos-gerenciador/pedidos-gerenciador.component')
      .then(m => m.PedidosGerenciadorComponent)
  },
] as Routes;