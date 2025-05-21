import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DsLayoutContentComponent } from '@dsmpf/ngx-dsmpf/layout/content';
import { DsMenu } from '@dsmpf/ngx-dsmpf/menu';
import { DsSidebarComponent, DsSidebarMenuComponent } from '@dsmpf/ngx-dsmpf/menu/sidebar';

@Component({
  selector: 'app-home',
  imports: [
    RouterOutlet,
    DsLayoutContentComponent,
    DsSidebarComponent,
    DsSidebarMenuComponent
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class HomeComponent {

  protected menuPedidos: DsMenu = {
    titulo: "Home",
    destacarRotaAtiva: true,
    itens: [
      {
        rotulo: 'Catálogo de serviços',
        link: './catalogo',
        icone: 'la-list-alt'
      },
      {
        separador: 'secao',
        rotulo: 'Pedidos'
      },
      {
        rotulo: 'Meus pedidos',
        link: './meus-pedidos',
        icone: 'la-list'
      },
      {
        rotulo: 'Painel de gerenciamento',
        link: './pedidos',
        icone: 'la-headset'
      }
    ]
  };

}
