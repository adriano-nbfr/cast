import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./dashboard/dashboard.component').then(m => m.HomeComponent),
    children: [
      // {
      //   path: 'meus-pedidos',
      // },
      {
        path: 'pedidos',
        loadChildren: () => import('./pedidos/pedidos.routes')
      }
    ]
  },
  {
    path: 'catalogo',
    loadChildren: () => import('./catalogo/catalogo.routes')
  }
];
