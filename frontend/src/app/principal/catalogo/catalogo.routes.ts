import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, Routes } from "@angular/router";
import { CatalogoApi } from "./catalogo-api";

export default [
  {
    path: '',
    title: 'Catálogo - Categorias',
    resolve: {
      categorias: () => inject(CatalogoApi).carregarCategorias()
    },
    loadComponent: () => import('./catalogo').then(m => m.Catalogo)
  },
  {
    path: ':idCategoria',
    title: 'Catálogo - Serviços',
    resolve: {
      servicos:(activatedRoute: ActivatedRouteSnapshot) =>
        inject(CatalogoApi).carregarServicos(activatedRoute.params['idCategoria'])
    },
    loadComponent: () => import('./catalogo-servicos/catalogo-servicos').then(m => m.CatalogoServicos)
  }
] as Routes;
