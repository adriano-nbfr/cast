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
      servicos:(activeRoute: ActivatedRouteSnapshot) =>
        inject(CatalogoApi).carregarServicos(activeRoute.params['idCategoria']),
      categoria: (activeRoute: ActivatedRouteSnapshot) =>
        inject(CatalogoApi).obterCategoria(activeRoute.params['idCategoria']),
    },
    loadComponent: () => import('./catalogo-servicos/catalogo-servicos').then(m => m.CatalogoServicos)
  },
] as Routes;
