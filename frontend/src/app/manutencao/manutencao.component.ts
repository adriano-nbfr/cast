import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DsLayoutContentComponent } from '@dsmpf/ngx-dsmpf/layout/content';
import { DsLayoutModuloComponent } from '@dsmpf/ngx-dsmpf/layout/modulo';
import { DsMenu } from '@dsmpf/ngx-dsmpf/menu';
import { DsMenuPrincipalComponent } from '@dsmpf/ngx-dsmpf/menu/principal';

@Component({
  selector: 'app-manutencao',
  imports: [
    RouterOutlet,
    DsLayoutModuloComponent,
    DsLayoutContentComponent,
    DsMenuPrincipalComponent
  ],
  templateUrl: './manutencao.component.html',
  styleUrl: './manutencao.component.scss'
})
export class ManutencaoComponent {

  protected menu: DsMenu = {
    destacarRotaAtiva: true,
    itens: [
      {
        rotulo: 'Início',
        link: '/home',
        icone: 'la-home'
      },
      {
        rotulo: 'Grupo de atendimento',
        link: './grupos-atendimento',
        icone: 'la-users',
      },
      {
        rotulo: 'Tabelas',
        icone: 'la-th',
        submenu: [
          {
            rotulo: 'Categoria',
            link: './categorias',
            icone: 'la-list'
          },
          {
            rotulo: 'Serviço',
            link: './servicos',
            icone: 'la-tools'
          }
        ]
      }
    ]
  };

}
