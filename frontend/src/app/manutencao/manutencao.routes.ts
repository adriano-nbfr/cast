import { Routes } from "@angular/router";

export default [
  {
    path: '',
    title: 'Manutenção',
    loadComponent: () => import('./manutencao').then(m => m.Manutencao)
  }
] as Routes;
