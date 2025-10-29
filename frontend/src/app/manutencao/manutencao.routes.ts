import { Routes } from "@angular/router";

export default [
  {
    path: '',
    title: 'Manutenção',
    loadComponent: () => import('./manutencao').then(m => m.Manutencao),
    children: [
      {
        path: 'categorias',
        loadChildren: () => import('./categorias/categorias.routes')
      },
      {
        path: 'servicos',
        loadChildren: () => import('./servicos/servicos.routes')
      }
    ]
  }
] as Routes;
