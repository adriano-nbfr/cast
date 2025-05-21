import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DsLayoutContentComponent } from '@dsmpf/ngx-dsmpf/layout/content';
import { DsMenu } from '@dsmpf/ngx-dsmpf/menu';
import { DsSidebarComponent, DsSidebarMenuComponent, DsSidebarMenuItemComponent } from '@dsmpf/ngx-dsmpf/menu/sidebar';

@Component({
  selector: 'app-home',
  imports: [
    RouterOutlet,
    DsLayoutContentComponent,
    DsSidebarComponent,
    DsSidebarMenuComponent,
    DsSidebarMenuItemComponent
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  protected menuPedidos: DsMenu = {
    destacarRotaAtiva: true,
    itens: [
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
