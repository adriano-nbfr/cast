import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, Routes } from "@angular/router";
import { CatalogoApi } from "./catalogo-api";

export default [
  {
    path: ':idCategoria',
    title: 'Catálogo - Serviços',
    resolve: {
      servicos: (route: ActivatedRouteSnapshot) =>
        inject(CatalogoApi).carregarServicos(route.params['idCategoria'])
    },
    loadComponent: () => import('./catalogo-servicos/catalogo-servicos')
      .then(m => m.CatalogoServicos)
  },
  {
    path: '',
    title: 'Catálogo - Categorias',
    loadComponent: () => import('./catalogo').then(m => m.Catalogo),
    resolve: {
      categorias: () => inject(CatalogoApi).carregarCategorias()
    }
  }
] as Routes;
