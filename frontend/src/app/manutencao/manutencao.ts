import { ChangeDetectionStrategy, Component, computed, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DsLayoutContentComponent } from '@dsmpf/ngx-dsmpf/layout/content';
import { DsLayoutModuloComponent } from '@dsmpf/ngx-dsmpf/layout/modulo';
import { DsMenu } from '@dsmpf/ngx-dsmpf/menu';
import { DsMenuPrincipalComponent } from '@dsmpf/ngx-dsmpf/menu/principal';
import { DsAppSeguranca } from '@dsmpf/ngx-dsmpf/seguranca';
import { environment } from '../../environment';

@Component({
  selector: 'app-manutencao',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    RouterOutlet,
    DsLayoutModuloComponent,
    DsLayoutContentComponent,
    DsMenuPrincipalComponent
  ],
  templateUrl: './manutencao.html',
  styleUrl: './manutencao.scss'
})
export class Manutencao {

  private appSeguranca = inject(DsAppSeguranca);

  // Recomendado que um menu seja definido no código como um signal que
  // reage a mudanças nos papeis do usuário autenticado, em caso de troca de atuação.
  menu = computed(() => {
    const isGerente = this.appSeguranca.isUsuarioAutorizado(environment.papeis.PAPEL_GERENTE);

    const menu: DsMenu = {
      destacarRotaAtiva: true,
      itens: [
        {
          rotulo: 'Início',
          link: '/',
          icone: 'la-home'
        },
        { separador: true },
        {
          rotulo: 'Grupo de atendimento',
          link: './grupos-atendimento',
          icone: 'la-users',
          desabilitado: !isGerente
        },
        {
          rotulo: 'Tabelas',
          icone: 'la-th',
          desabilitado: !isGerente,
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

    return menu;
  });


}
