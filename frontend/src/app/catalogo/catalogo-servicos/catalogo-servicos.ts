import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DsCardComponent, DsCardContentComponent, DsCardFooterComponent, DsCardHeaderTituloComponent } from "@dsmpf/ngx-dsmpf/conteudo/card";
import { DsLayoutContentComponent } from "@dsmpf/ngx-dsmpf/layout/content";
import { Categoria } from '../../shared/model/categoria';
import { Servico } from '../../shared/model/servico';
import { DsBotaoIconeComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes/icone';
import { DsBotaoComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes';


@Component({
  selector: 'app-catalogo-servicos',
  imports: [
    RouterLink,
    DsLayoutContentComponent,
    DsCardComponent,
    DsCardHeaderTituloComponent,
    DsCardContentComponent,
    DsCardFooterComponent,
    DsBotaoComponent,
    DsBotaoIconeComponent
  ],
  templateUrl: './catalogo-servicos.html',
  styleUrl: './catalogo-servicos.scss'
})
export class CatalogoServicos {

  categoria = input.required<Categoria>();

  servicos = input<Servico[]>([]);

}
