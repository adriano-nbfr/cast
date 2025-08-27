import { inject } from '@angular/core';
import { Routes } from '@angular/router';
import { DsAppSeguranca } from '@dsmpf/ngx-dsmpf/seguranca';
import { environment } from '../environment';

export const routes: Routes = [
  // novas rotas entram antes da rota com o path vazio, devido ao match default por prefixo
  {
    path: 'manutencao',
    canMatch: [
      () => inject(DsAppSeguranca).isUsuarioAutorizadoAssincrono(environment.papeis.PAPEL_GERENTE, true)
    ],
    loadChildren: () => import('./manutencao/manutencao.routes')
  },
  {
    path: '',
    canMatch: [
      () => inject(DsAppSeguranca).isUsuarioAutenticadoAssincrono(true)
    ],
    loadChildren: () => import('./principal/principal.routes')
  }
];
