import { Routes } from "@angular/router";
import { ManutencaoComponent } from "./manutencao.component";

export default [
  {
    path: '',
    title: 'Manutenção',
    component: ManutencaoComponent,
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