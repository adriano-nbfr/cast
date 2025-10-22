import { Routes } from "@angular/router";

export default [

  {
    path: '',
    loadComponent: () => import('./principal').then(m => m.Principal),
    children: [
      {
        path: 'catalogo',
        loadChildren: () => import('./catalogo/catalogo.routes')
      }
    ]
  }

] as Routes;
