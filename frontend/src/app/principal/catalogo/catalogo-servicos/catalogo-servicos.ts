import { ChangeDetectionStrategy, Component, computed, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DsCardImports } from "@dsmpf/ngx-dsmpf/conteudo/card";
import { DsBotaoComponent } from "@dsmpf/ngx-dsmpf/elementos/botoes";
import { DsBotaoIconeComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes/icone';
import { Servico } from '../../../shared/model/servico';

@Component({
  selector: 'app-catalogo-servicos',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    RouterLink,
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
