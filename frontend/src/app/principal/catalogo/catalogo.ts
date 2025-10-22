import { Component, inject, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DsBotaoComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes';
import { Categoria } from '../../shared/model/categoria';
import { CatalogoApi } from './catalogo-api';

@Component({
  selector: 'app-catalogo',
  imports: [
    RouterLink,
    DsBotaoComponent
  ],
  templateUrl: './catalogo.html',
  styleUrl: './catalogo.scss'
})
export class Catalogo {

  categorias = input<Categoria[]>([]);

  private catalogApi = inject(CatalogoApi);

  protected endpointBase = this.catalogApi.endpointBaseCategorias;

}
