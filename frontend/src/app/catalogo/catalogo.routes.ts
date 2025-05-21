import { Routes } from "@angular/router";
import { CatalogoComponent } from "./catalogo.component";
import { inject } from "@angular/core";
import { CatalogoService } from "./catalogo.service";

export default [
  {
    path: '',
    title: 'Catálogo - categorias',
    resolve: {
      categorias: () => inject(CatalogoService).carregarCategorias()
    },
    component: CatalogoComponent
  }
] as Routes;
