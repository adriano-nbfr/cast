import { inject } from '@angular/core';
import { Routes } from '@angular/router';
import { DsAppSeguranca } from '@dsmpf/ngx-dsmpf/seguranca';

export const routes: Routes = [
  // novas rotas entram antes da rota com o path vazio, devido ao match default por prefixo
  {
    path: '',
    canMatch: [
      () => inject(DsAppSeguranca).isUsuarioAutenticadoAssincrono()
    ],
    loadChildren: () => import('./principal/principal.routes')
  }
];
