import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, Routes } from "@angular/router";
import { CatalogoService } from "./catalogo.service";

export default [
  {
    path: '',
    title: 'Catálogo - categorias',
    resolve: {
      categorias: () => inject(CatalogoService).carregarCategorias()
    },
    loadComponent: () => import('./catalogo.component')
      .then(m => m.CatalogoComponent)
  },
  {
    path: ':idCategoria',
    title: 'Catálogo - serviços',
    resolve: {
      servicos:(activeRoute: ActivatedRouteSnapshot) =>
        inject(CatalogoService).carregarServicos(activeRoute.params['idCategoria']),
      categoria: (activeRoute: ActivatedRouteSnapshot) =>
        inject(CatalogoService).obterCategoria(activeRoute.params['idCategoria']),
    },
    loadComponent: () => import('./catalogo-servicos/catalogo-servicos.component')
      .then(m => m.CatalogoServicosComponent)
  },
] as Routes;
