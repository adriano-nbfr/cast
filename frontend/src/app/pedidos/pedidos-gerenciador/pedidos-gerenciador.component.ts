import { Component, inject, OnInit } from '@angular/core';
import { DsConteudoImports } from '@dsmpf/ngx-dsmpf/conteudo';
import { DsCardImports } from '@dsmpf/ngx-dsmpf/conteudo/card';
import { DsDatasourceRestFactory } from '@dsmpf/ngx-dsmpf/datasource';
import { DsDatatableColunaDef, DsDatatableComponent } from '@dsmpf/ngx-dsmpf/datasource/datatable';
import { Pedido } from '../../shared/model/pedido';
import { PedidosService } from '../pedidos.service';

@Component({
  selector: 'app-pedidos-gerenciador',
  imports: [
    DsConteudoImports,
    DsDatatableComponent,
    DsCardImports
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
    {id: 'titulo', titulo: 'Título', ordenavel: true, largura: '20%'},
    {id: 'descricao', titulo: 'Descrição', ordenavel: false, conteudoLimitado: '1-linha'},
    {id: 'usuarioSolicitante.nome', titulo: 'Solicitante', ordenavel: true, largura: '20%'},
    {id: 'dataAbertura', titulo: 'Abertura', ordenavel: true, largura: '130', formatoData: 'dd/MM/yyyy HH:mm'},
  ];


  ngOnInit() {
    this.datasource.conectar();
  }


  consultar(filtro: object) {
    this.datasource.filtro = filtro;
    this.datasource.atualizar();
  }

}
