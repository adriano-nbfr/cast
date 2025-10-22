import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'catalogo',
    loadChildren: () => import('./catalogo/catalogo.routes')
  }
];
