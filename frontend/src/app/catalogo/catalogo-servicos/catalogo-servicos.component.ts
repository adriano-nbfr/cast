import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DsCardImports } from '@dsmpf/ngx-dsmpf/conteudo/card';
import { DsBotaoIconeComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes/icone';
import { DsLayoutContentComponent } from '@dsmpf/ngx-dsmpf/layout/content';
import { Categoria } from '../../shared/model/categoria';
import { Servico } from '../../shared/model/servico';
import { DsBotaoComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes';

@Component({
  selector: 'app-catalogo-servicos',
  imports: [
    RouterLink,
    DsLayoutContentComponent,
    DsCardImports,
    DsBotaoIconeComponent,
    DsBotaoComponent
  ],
  templateUrl: './catalogo-servicos.component.html',
  styleUrl: './catalogo-servicos.component.scss'
})
export class CatalogoServicosComponent {

  categoria = input.required<Categoria>();

  servicos = input<Servico[]>([]);

}
