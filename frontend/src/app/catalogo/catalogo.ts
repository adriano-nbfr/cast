import { Component, inject, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DsBotaoComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes';
import { DsLayoutContentComponent } from "@dsmpf/ngx-dsmpf/layout/content";
import { Categoria } from '../shared/model/categoria';
import { CatalogoApi } from './catalogo-api';

@Component({
  selector: 'app-catalogo',
  imports: [
    RouterLink,
    DsLayoutContentComponent,
    DsBotaoComponent
  ],
  templateUrl: './catalogo.html',
  styleUrl: './catalogo.scss'
})
export class Catalogo {

  categorias = input<Categoria[]>([]);

  private catalogoApi = inject(CatalogoApi);

  protected endpointBase = this.catalogoApi.endpointBaseCategorias;

}
