import { Component, computed, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DsLayoutContentComponent } from '@dsmpf/ngx-dsmpf/layout/content';
import { DsMenu } from '@dsmpf/ngx-dsmpf/menu';
import { DsSidebarComponent, DsSidebarMenuComponent } from "@dsmpf/ngx-dsmpf/menu/sidebar";
import { DsAppSeguranca } from '@dsmpf/ngx-dsmpf/seguranca';
import { environment } from '../../environment';

@Component({
  selector: 'app-principal',
  imports: [
    RouterOutlet,
    DsLayoutContentComponent,
    DsSidebarComponent,
    DsSidebarMenuComponent
],
  templateUrl: './principal.html',
  styleUrl: './principal.scss'
})
export class Principal {

  private appSeguranca = inject(DsAppSeguranca);

  /* O menu é um signal que reage automaticamene às mudanças na lista de papéis do usuário autenticado,
  armazenada como um signal internamente ao DsAppSeguranca, sem ter que recarregar a aplicação inteira */
  protected menuPedidos = computed(() => {
    const menu: DsMenu = {
      destacarRotaAtiva: true,
      itens: [
        {
          rotulo: 'Catálgo de serviços',
          link: '/catalogo',
          icone: 'la-grip-horizontal'
        },
        {
          separador: true
        },
        {
          rotulo: 'Meus pedidos',
          link: '/pedidos/meus-pedidos',
          icone: 'la-list'
        }
      ]
    };

    if (this.appSeguranca.isUsuarioAutorizado(environment.papeis.PAPEL_ATENDENTE)) {
      menu.itens.push({
        rotulo: 'Painel gerencial',
        link: '/pedidos/painel-gerencial',
        icone: 'la-headset'
      });
    }

    return menu;
  });

}
