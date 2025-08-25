import { Component, inject, model, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { DsConteudoImports } from '@dsmpf/ngx-dsmpf/conteudo';
import { DsCardImports } from '@dsmpf/ngx-dsmpf/conteudo/card';
import { DsDatatableColunaDef, DsDatatableImports } from '@dsmpf/ngx-dsmpf/datasource/datatable';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { TiposUrgenciaPedido, UrgenciaPedidoPipe } from '../../../shared/pipes/urgencia-pedido.pipe';
import { StatusPedidoPipe } from '../../../shared/pipes/status-pedido.pipe';
import { CorStatusPedidoPipe } from '../../../shared/pipes/cor-status-pedido.pipe';
import { DsBotaoComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes';
import { DsBotaoIconeComponent, DsBotaoIconeVerticalComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes/icone';
import { DsAutocompletarDirective } from '@dsmpf/ngx-dsmpf/form/autocompletar';
import { DsUploadArquivoComponent } from '@dsmpf/ngx-dsmpf/arquivo';
import { Pedido } from '../../../shared/model/pedido';
import { PedidosApi } from '../pedidos-api';
import { DS_RAIZ_API } from '@dsmpf/ngx-dsmpf/configuracao';
import { DsDatasourceMemoria } from '@dsmpf/ngx-dsmpf/datasource';
import { GrupoAtendimento } from '../../../shared/model/grupo-atendimento';
import { Usuario } from '../../../shared/model/usuario';
import { Andamento } from '../../../shared/model/andamento';
import { DsAppContent } from '@dsmpf/ngx-dsmpf/layout/content';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-pedido-edicao',
  imports: [
    ReactiveFormsModule,
    RouterLink,
    DsConteudoImports,
    DsDatatableImports,
    DsCardImports,
    DsFormImports,
    UrgenciaPedidoPipe,
    StatusPedidoPipe,
    CorStatusPedidoPipe,
    DsBotaoComponent,
    DsBotaoIconeComponent,
    DsBotaoIconeVerticalComponent,
    DsAutocompletarDirective,
    DsUploadArquivoComponent,
  ],
  templateUrl: './pedido-edicao.html',
  styleUrl: './pedido-edicao.scss'
})
export class PedidoEdicao {

  pedido = model.required<Pedido>();

  protected pedidosApi = inject(PedidosApi);

  protected appContent = inject(DsAppContent);

  protected raizApi = inject(DS_RAIZ_API);

  protected editandoGrupo = signal(false);

  protected editandoSolicitante = signal(false);

  protected editandoAtendente = signal(false);

  protected datasourceAndamentos = new DsDatasourceMemoria();

  protected opcoesUrgencia = TiposUrgenciaPedido;

  private fb = new FormBuilder();

  protected formPedido = this.fb.group({
    titulo: this.fb.control('', {validators: [Validators.required, Validators.minLength(10)]}),
    descricao: this.fb.control('', {validators: [Validators.required, Validators.minLength(50)]}),
    urgencia: this.fb.control(2, {validators: [Validators.required], nonNullable: true}),
    grupoAtendimento: this.fb.control<GrupoAtendimento | null>(null, [Validators.required]),
    usuarioSolicitante: this.fb.control<Usuario | null>(null, {validators: [Validators.required]}),
    usuarioAtendente: this.fb.control<Usuario | null>(null),
  });

  protected buscarGrupo = this.pedidosApi
    .criarFuncaoAutocompletar<GrupoAtendimento>('buscar/grupos');

  protected buscarUsuario = this.pedidosApi
    .criarFuncaoAutocompletar<Usuario>('buscar/usuarios');


  protected colunas: DsDatatableColunaDef<Andamento>[] = [
    {id: 'usuario.nome', titulo: 'Usuário', ordenavel: false, largura: '180'},
    {id: 'dataRegistro', titulo: 'Registro', ordenavel: true, largura: '130', formatoData: 'dd/MM/yyyy HH:mm'},
  ];


  ngOnInit() {
    this.atualizarFormComPedidoCarregado();
    this.inicializarDatasourceAndamentos();
  }

  protected atualizarFormComPedidoCarregado() {
    this.formPedido.patchValue(this.pedido());
    this.formPedido.markAsPristine();
    this.editandoGrupo.set(false);
    this.editandoAtendente.set(false);
    this.editandoSolicitante.set(false);

    if (this.pedido().status === 'F')
      this.formPedido.controls.urgencia.disable();
    else
      this.formPedido.controls.urgencia.enable();
  }

  private inicializarDatasourceAndamentos() {
    const idPedido = this.pedido().id ?? '';
    this.datasourceAndamentos.definirFonteDados(
      this.pedidosApi.obterAndamentosPedido(idPedido)
    );

    this.datasourceAndamentos.ordenacao = {coluna: 'dataRegistro', sentido: 'desc'};
    this.datasourceAndamentos.conectar();
  }

  protected carregarAndamentos() {
    this.datasourceAndamentos.atualizar();
  }

  protected suspender() {
    if (this.pedido().status !== 'E' && this.pedido().status !== 'F') {
      this.appContent.bloquear();
      this.pedidosApi.suspenderPedido(this.pedido())
        .pipe(finalize(() => this.appContent.desbloquear()))
        .subscribe(pedido => {
          this.pedido.update(p => ({...p, status: pedido.status}));
          this.carregarAndamentos();
        });
    }
  }

}
