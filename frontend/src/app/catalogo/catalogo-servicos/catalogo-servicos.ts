import { Component, computed, input } from '@angular/core';
import { DsCardImports } from "@dsmpf/ngx-dsmpf/conteudo/card";
import { DsLayoutContentComponent } from "@dsmpf/ngx-dsmpf/layout/content";
import { Servico } from '../../shared/model/servico';
import { RouterLink } from '@angular/router';
import { DsBotaoIconeComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes/icone';
import { DsBotaoComponent } from "@dsmpf/ngx-dsmpf/elementos/botoes";

@Component({
  selector: 'app-catalogo-servicos',
  imports: [
    RouterLink,
    DsLayoutContentComponent,
    DsCardImports,
    DsBotaoIconeComponent,
    DsBotaoComponent
],
  templateUrl: './catalogo-servicos.html',
  styleUrl: './catalogo-servicos.scss'
})
export class CatalogoServicos {

  servicos = input.required<Servico[]>();

  protected categoria = computed(() => {
    return this.servicos().length > 0 ? this.servicos()[0].categoria : undefined;
  });

}
