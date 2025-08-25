import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { PedidosApi } from '../pedidos-api';
import { FormsModule } from '@angular/forms';
import { DsConteudoImports } from '@dsmpf/ngx-dsmpf/conteudo';
import { DsCardImports } from '@dsmpf/ngx-dsmpf/conteudo/card';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { DsDatatableColunaDef, DsDatatableImports } from '@dsmpf/ngx-dsmpf/datasource/datatable';
import { DsDatepickerDirective } from '@dsmpf/ngx-dsmpf/form/datepicker';
import { DsAutocompletarDirective } from '@dsmpf/ngx-dsmpf/form/autocompletar';
import { DsBotaoComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes';
import { CorStatusPedidoPipe } from '../../../shared/pipes/cor-status-pedido.pipe';
import { StatusPedidoPipe } from '../../../shared/pipes/status-pedido.pipe';
import { UrgenciaPedidoPipe } from '../../../shared/pipes/urgencia-pedido.pipe';
import { Pedido } from '../../../shared/model/pedido';

@Component({
  selector: 'app-painel-gerencial',
  imports: [
    FormsModule,
    RouterLink,
    DsConteudoImports,
    DsCardImports,
    DsFormImports,
    DsDatatableImports,
    DsDatepickerDirective,
    DsAutocompletarDirective,
    DsBotaoComponent,
    CorStatusPedidoPipe,
    StatusPedidoPipe,
    UrgenciaPedidoPipe
  ],
  templateUrl: './painel-gerencial.html',
  styleUrl: './painel-gerencial.scss'
})
export class PainelGerencial {

  private router = inject(Router);

  private pedidosApi = inject(PedidosApi);

  protected datasource = this.pedidosApi.obterDatasourcePainelGerencial();

  protected colunas: DsDatatableColunaDef<Pedido>[] = [
    // {id: 'id', titulo: 'Número', ordenavel: false, largura: '50'},
    // {id: 'urgencia', titulo: 'Urgência', ordenavel: false, largura: '60'},
    // {id: 'status', titulo: 'Status', ordenavel: false, largura: '50'},
    {id: 'titulo', titulo: 'Título', ordenavel: false, largura: '15%'},
    {id: 'descricao', titulo: 'Descrição', ordenavel: false, conteudoLimitado: '2-linhas'},
    {id: 'usuarioSolicitante.nome', titulo: 'Solicitante', ordenavel: true, largura: '180'},
    {id: 'usuarioAtendente.nome', titulo: 'Atendente', ordenavel: true, largura: '180'},
    {id: 'dataAbertura', titulo: 'Abertura', ordenavel: true, largura: '130', formatoData: 'dd/MM/yyyy HH:mm'},
  ];


  ngOnInit() {
    this.datasource.conectar();
  }

  ngOnDestroy() {
    this.datasource.desconectar();
  }

  consultar(filtro: object) {
    this.datasource.filtro = filtro;
    this.datasource.atualizar();
  }

  protected abrirPedido(id: number | string | null | undefined) {
    if (!id || typeof id === 'string' && isNaN(parseInt(id)))
      return;

    const url = this.router.serializeUrl(this.router.createUrlTree([`/pedidos/${id}`]));
    window.open(url, '_blank');
  }

}
