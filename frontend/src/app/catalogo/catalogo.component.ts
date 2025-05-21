import { Component, inject, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DsBotaoComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes';
import { DsLayoutContentComponent } from '@dsmpf/ngx-dsmpf/layout/content';
import { Categoria } from '../shared/model/categoria';
import { CatalogoService } from './catalogo.service';

@Component({
  selector: 'app-catalogo',
  imports: [
    RouterLink,
    DsLayoutContentComponent,
    DsBotaoComponent
  ],
  templateUrl: './catalogo.component.html',
  styleUrl: './catalogo.component.scss'
})
export class CatalogoComponent {

  categorias = input<Categoria[]>([]);

  private catalogoService = inject(CatalogoService);

  protected endpointBase = this.catalogoService.endpointBaseCategorias;

}
