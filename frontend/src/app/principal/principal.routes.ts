import { Routes } from "@angular/router";

export default [

  {
    path: '',
    loadComponent: () => import('./principal').then(m => m.Principal),
    children: [
      {
        path: 'catalogo',
        loadChildren: () => import('./catalogo/catalogo.routes')
      },
      {
        path: 'pedidos',
        loadChildren: () => import('./pedidos/pedidos.routes')
      },
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'pedidos'
      }
    ]
  }

] as Routes;
