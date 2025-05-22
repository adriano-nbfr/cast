import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./dashboard/dashboard.component').then(m => m.DashboardComponent),
    children: [
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
