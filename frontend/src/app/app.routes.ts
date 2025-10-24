import { inject } from '@angular/core';
import { Routes } from '@angular/router';
import { DsAppSeguranca } from '@dsmpf/ngx-dsmpf/seguranca';

export const routes: Routes = [
  {
    path: '',
    canMatch: [
      () => inject(DsAppSeguranca).isUsuarioAutenticadoAssincrono(true)
    ],
    loadChildren: () => import('./principal/principal.routes')
  }
];
