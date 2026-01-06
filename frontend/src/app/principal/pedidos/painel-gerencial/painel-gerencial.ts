import { ChangeDetectionStrategy, Component, inject, OnDestroy, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { DsConteudoImports } from '@dsmpf/ngx-dsmpf/conteudo';
import { DsCardImports } from '@dsmpf/ngx-dsmpf/conteudo/card';
import { DsDatatableAcaoDef, DsDatatableColunaDef, DsDatatableImports } from '@dsmpf/ngx-dsmpf/datasource/datatable';
import { DsBotaoComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { DsAutocompletarDirective } from '@dsmpf/ngx-dsmpf/form/autocompletar';
import { DsDatepickerDirective } from "@dsmpf/ngx-dsmpf/form/datepicker";
import { Pedido } from '../../../shared/model/pedido';
import { Usuario } from '../../../shared/model/usuario';
import { CorStatusPedidoPipe } from '../../../shared/pipes/cor-status-pedido.pipe';
import { StatusPedidoPipe, TiposStatusPedido } from '../../../shared/pipes/status-pedido.pipe';
import { TiposUrgenciaPedido, UrgenciaPedidoPipe } from '../../../shared/pipes/urgencia-pedido.pipe';
import { PedidosApi } from '../pedidos-api';

@Component({
  selector: 'app-painel-gerencial',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    FormsModule,
    RouterLink,
    DsConteudoImports,
    DsCardImports,
    DsFormImports,
    DsDatatableImports,
    DsBotaoComponent,
    DsDatepickerDirective,
    DsAutocompletarDirective,
    UrgenciaPedidoPipe,
    StatusPedidoPipe,
    CorStatusPedidoPipe,
],
  templateUrl: './painel-gerencial.html',
  styleUrl: './painel-gerencial.scss'
})
export class PainelGerencial implements OnInit, OnDestroy {

  private router = inject(Router);

  private pedidosApi = inject(PedidosApi);

  protected opcoesUrgencia = TiposUrgenciaPedido;

  protected opcoesStatus = TiposStatusPedido;

  protected datasource = this.pedidosApi.obterDatasourcePainelGerencial();


  protected buscarUsuario = this.pedidosApi
    .criarFuncaoAutocompletar<Usuario>('buscar/usuarios');


  protected colunas: DsDatatableColunaDef<Pedido>[] = [
    // { id: 'id', titulo: 'Número', ordenavel: true, largura: '50' },
    // { id: 'urgencia', titulo: 'Urgência', ordenavel: true, largura: '60' },
    // { id: 'status', titulo: 'Status', ordenavel: true, largura: '50' },
    { id: 'titulo', titulo: 'Título', ordenavel: false, largura: '15%' },
    { id: 'descricao', titulo: 'Descrição', ordenavel: false, conteudoLimitado: '2-linhas'},
    { id: 'usuarioSolicitante.nome', titulo: 'Solicitante', ordenavel: true, largura: '180'},
    { id: 'usuarioAtendente.nome', titulo: 'Atendente', ordenavel: true, largura: '180'},
    { id: 'dataAbertura', titulo: 'Abertura', ordenavel: true, largura: '130', formatoData: 'dd/MM/yyyy HH:mm' }
  ];


  protected acoes: DsDatatableAcaoDef<Pedido>[] = [
    {
      id: 'abrir',
      descricao: 'Abrir o pedido em uma nova aba',
      icone: 'la-edit',
      cor: 'primary',
      funcao: (pedido) => this.abrirPedido(pedido.id)
    }
  ];


  ngOnInit() {
    this.datasource.conectar();
  }

  ngOnDestroy() {
    this.datasource.desconectar();
  }

  protected consultar(filtro: object) {
    this.datasource.filtro = filtro;
    this.datasource.atualizar();
  }

  protected abrirPedido(id: number | string | null | undefined) {
    if (!id || typeof id === 'string' && isNaN(parseInt(id)))
      return;

    const url = this.router.serializeUrl(this.router.createUrlTree([`/cast/pedidos/${id}`]));
    window.open(url, '_blank');
  }

}
