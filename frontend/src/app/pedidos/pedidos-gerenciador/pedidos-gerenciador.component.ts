import { Component, inject, OnInit } from '@angular/core';
import { DsConteudoImports } from '@dsmpf/ngx-dsmpf/conteudo';
import { DsCardImports } from '@dsmpf/ngx-dsmpf/conteudo/card';
import { DsDatasourceRestFactory } from '@dsmpf/ngx-dsmpf/datasource';
import { DsDatatableColunaDef, DsDatatableImports } from '@dsmpf/ngx-dsmpf/datasource/datatable';
import { Pedido } from '../../shared/model/pedido';
import { PedidosService } from '../pedidos.service';
import { StatusPedidoPipe, TiposStatusPedido } from '../status-pedido.pipe';
import { TiposUrgenciaPedido, UrgenciaPedidoPipe } from '../urgencia-pedido.pipe';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { FormsModule } from '@angular/forms';
import { DsDatepickerDirective } from '@dsmpf/ngx-dsmpf/form/datepicker';
import { DsBotaoComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes';
import { DsAutocompletarDirective } from '@dsmpf/ngx-dsmpf/form/autocompletar';
import { Usuario } from '../../shared/model/usuario';

@Component({
  selector: 'app-pedidos-gerenciador',
  imports: [
    FormsModule,
    DsConteudoImports,
    DsDatatableImports,
    DsCardImports,
    DsFormImports,
    DsDatepickerDirective,
    DsAutocompletarDirective,
    DsBotaoComponent,
    UrgenciaPedidoPipe,
    StatusPedidoPipe
  ],
  templateUrl: './pedidos-gerenciador.component.html',
  styleUrl: './pedidos-gerenciador.component.scss'
})
export class PedidosGerenciadorComponent implements OnInit {

  private pedidosService = inject(PedidosService);

  private factory = inject(DsDatasourceRestFactory);

  protected datasource = this.factory
    .criarDatasourceRest<Pedido>(this.pedidosService.endpointApiRecurso);

  protected colunas: DsDatatableColunaDef<Pedido>[] = [
    {id: 'id', titulo: 'Número', ordenavel: false, largura: '70', alinhamento: 'center'},
    {id: 'titulo', titulo: 'Título', ordenavel: false, largura: '15%'},
    {id: 'descricao', titulo: 'Descrição', ordenavel: false, conteudoLimitado: '2-linhas'},
    {id: 'usuarioSolicitante.nome', titulo: 'Solicitante', ordenavel: true, largura: '180'},
    {id: 'usuarioAtendente.nome', titulo: 'Atendente', ordenavel: true, largura: '180'},
    {id: 'dataAbertura', titulo: 'Abertura', ordenavel: true, largura: '130', formatoData: 'dd/MM/yyyy HH:mm'},
  ];

  protected opcoesUrgencia = TiposUrgenciaPedido;

  protected opcoesStatus = TiposStatusPedido;

  protected buscarUsuario = this.pedidosService
    .criarFuncaoAutocompletar<Usuario>('buscar/usuarios');


  ngOnInit() {
    this.datasource.conectar();
  }


  consultar(filtro: object) {
    this.datasource.filtro = filtro;
    this.datasource.atualizar();
  }

}
