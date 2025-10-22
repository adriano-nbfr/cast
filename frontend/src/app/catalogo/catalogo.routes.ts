import { inject } from "@angular/core";
import { Routes } from "@angular/router";
import { CatalogoApi } from "./catalogo-api";

export default [
  {
    path: '',
    title: 'Catálogo - Categorias',
    loadComponent: () => import('./catalogo').then(m => m.Catalogo),
    resolve: {
      categorias: () => inject(CatalogoApi).carregarCategorias()
    }
  }
] as Routes;
