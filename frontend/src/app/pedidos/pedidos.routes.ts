import { Routes } from "@angular/router";

export default [
  {
    path: '',
    loadComponent: () => import('./pedidos-gerenciador/pedidos-gerenciador.component')
      .then(m => m.PedidosGerenciadorComponent)
  }
] as Routes;