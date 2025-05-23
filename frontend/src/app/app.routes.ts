import { inject } from '@angular/core';
import { Routes } from '@angular/router';
import { DsAppSeguranca } from '@dsmpf/ngx-dsmpf/seguranca';
import { environment } from '../environment';

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
  },
  {
    path: 'manutencao',
    canMatch: [
      () => inject(DsAppSeguranca).isUsuarioAutorizadoAssincrono(environment.papeis.PAPEL_GERENTE, true)
    ],
    loadChildren: () => import('./manutencao/manutencao.routes')
  }
];
