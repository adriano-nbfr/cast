import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { DsConteudoImports } from '@dsmpf/ngx-dsmpf/conteudo';
import { DsCardImports } from '@dsmpf/ngx-dsmpf/conteudo/card';
import { DsDatasourceRestFactory } from '@dsmpf/ngx-dsmpf/datasource';
import { DsDatatableAcaoDef, DsDatatableColunaDef, DsDatatableImports } from '@dsmpf/ngx-dsmpf/datasource/datatable';
import { DsBotaoComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { DsAutocompletarDirective } from '@dsmpf/ngx-dsmpf/form/autocompletar';
import { DsDatepickerDirective } from '@dsmpf/ngx-dsmpf/form/datepicker';
import { Pedido } from '../../shared/model/pedido';
import { Usuario } from '../../shared/model/usuario';
import { PedidosService } from '../pedidos.service';
import { StatusPedidoPipe, TiposStatusPedido } from '../status-pedido.pipe';
import { TiposUrgenciaPedido, UrgenciaPedidoPipe } from '../urgencia-pedido.pipe';
import { CorStatusPedidoPipe } from '../cor-status-pedido.pipe';

@Component({
  selector: 'app-pedidos-gerenciador',
  imports: [
    RouterLink,
    FormsModule,
    DsConteudoImports,
    DsDatatableImports,
    DsCardImports,
    DsFormImports,
    DsDatepickerDirective,
    DsAutocompletarDirective,
    DsBotaoComponent,
    UrgenciaPedidoPipe,
    StatusPedidoPipe,
    CorStatusPedidoPipe,
  ],
  templateUrl: './pedidos-gerenciador.component.html',
  styleUrl: './pedidos-gerenciador.component.scss'
})
export class PedidosGerenciadorComponent implements OnInit, OnDestroy {

  private pedidosService = inject(PedidosService);

  private router = inject(Router);

  private factory = inject(DsDatasourceRestFactory);

  protected datasource = this.factory
    .criarDatasourceRest<Pedido>(this.pedidosService.endpointApiRecurso);

  protected colunas: DsDatatableColunaDef<Pedido>[] = [
    {id: 'titulo', titulo: 'Título', ordenavel: false, largura: '15%'},
    {id: 'descricao', titulo: 'Descrição', ordenavel: false, conteudoLimitado: '2-linhas'},
    {id: 'usuarioSolicitante.nome', titulo: 'Solicitante', ordenavel: true, largura: '180'},
    {id: 'usuarioAtendente.nome', titulo: 'Atendente', ordenavel: true, largura: '180'},
    {id: 'dataAbertura', titulo: 'Abertura', ordenavel: true, largura: '130', formatoData: 'dd/MM/yyyy HH:mm'},
  ];

  // Ações customizadas representadas por um ícone no lado direito de cada linha
  protected acoes: DsDatatableAcaoDef<Pedido>[] = [
    {
      id: 'navegar',
      descricao: 'Navegar para o pedido',
      icone: 'la-edit',
      cor: 'primary',
      funcao: pedido => { this.router.navigateByUrl(`/pedidos/${pedido.id}`) }
    }
  ];

  protected opcoesUrgencia = TiposUrgenciaPedido;

  protected opcoesStatus = TiposStatusPedido;

  protected buscarUsuario = this.pedidosService
    .criarFuncaoAutocompletar<Usuario>('buscar/usuarios');


  ngOnInit() {
    this.datasource.tamanhoPagina = 5;
    this.datasource.conectar();
  }

  ngOnDestroy() {
    this.datasource.desconectar();
  }


  consultar(filtro: object) {
    this.datasource.filtro = filtro;
    this.datasource.atualizar();
  }

}
