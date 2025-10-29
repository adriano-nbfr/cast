import { inject } from '@angular/core';
import { Routes } from '@angular/router';
import { DsAppSeguranca } from '@dsmpf/ngx-dsmpf/seguranca';
import { environment } from '../environment';

export const routes: Routes = [
  {
    path: 'manutencao',
    loadChildren: () => import('./manutencao/manutencao.routes'),
    canMatch: [
      () => inject(DsAppSeguranca).isUsuarioAutorizadoAssincrono(environment.papeis.PAPEL_GERENTE, true)
    ]
  },
  {
    path: '',
    canMatch: [
      () => inject(DsAppSeguranca).isUsuarioAutenticadoAssincrono(true)
    ],
    loadChildren: () => import('./principal/principal.routes')
  }
];
