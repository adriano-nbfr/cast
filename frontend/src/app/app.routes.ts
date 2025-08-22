import { inject } from '@angular/core';
import { Routes } from '@angular/router';
import { DsAppSeguranca } from '@dsmpf/ngx-dsmpf/seguranca';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./principal/principal').then(m => m.Principal),
    children: [
    ]
  },
  {
    path: 'catalogo',
    canMatch: [
      () => inject(DsAppSeguranca).isUsuarioAutenticadoAssincrono(true)
    ],
    loadChildren: () => import('./catalogo/catalogo.routes')
  }
];
