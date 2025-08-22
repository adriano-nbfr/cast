import { inject } from "@angular/core";
import { Routes } from "@angular/router";
import { CatalogoApi } from "./catalogo-api";

export default [
  {
    path: '',
    title: 'Catálogo - Categorias',
    resolve: {
      categorias: () => inject(CatalogoApi).carregarCategorias()
    },
    loadComponent: () => import('./catalogo').then(m => m.Catalogo)
  }
] as Routes;
