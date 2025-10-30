import { ChangeDetectionStrategy, Component, inject, model, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { DsUploadArquivoComponent } from '@dsmpf/ngx-dsmpf/arquivo';
import { DsAppNotificacao } from '@dsmpf/ngx-dsmpf/basico';
import { DS_RAIZ_API } from '@dsmpf/ngx-dsmpf/configuracao';
import { DsConteudoImports } from '@dsmpf/ngx-dsmpf/conteudo';
import { DsCardImports } from '@dsmpf/ngx-dsmpf/conteudo/card';
import { DsDatasourceMemoria } from '@dsmpf/ngx-dsmpf/datasource';
import { DsDatatableColunaDef, DsDatatableImports } from '@dsmpf/ngx-dsmpf/datasource/datatable';
import { DsDialogImports } from '@dsmpf/ngx-dsmpf/dialog';
import { DsBotaoComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes';
import { DsBotaoIconeComponent, DsBotaoIconeVerticalComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes/icone';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { DsAutocompletarDirective } from '@dsmpf/ngx-dsmpf/form/autocompletar';
import { DsAppContent } from '@dsmpf/ngx-dsmpf/layout/content';
import { DsAppPopup } from '@dsmpf/ngx-dsmpf/popup';
import { finalize } from 'rxjs';
import { Andamento } from '../../../shared/model/andamento';
import { GrupoAtendimento } from '../../../shared/model/grupo-atendimento';
import { Pedido } from '../../../shared/model/pedido';
import { Usuario } from '../../../shared/model/usuario';
import { CorStatusPedidoPipe } from '../../../shared/pipes/cor-status-pedido.pipe';
import { StatusPedidoPipe } from '../../../shared/pipes/status-pedido.pipe';
import { TiposUrgenciaPedido, UrgenciaPedidoPipe } from '../../../shared/pipes/urgencia-pedido.pipe';
import { PedidosApi } from '../pedidos-api';

@Component({
  selector: 'app-pedido-edicao',
  changeDetection: ChangeDetectionStrategy.OnPush,
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
    DsDialogImports
  ],
  templateUrl: './pedido-edicao.html',
  styleUrl: './pedido-edicao.scss'
})
export class PedidoEdicao implements OnInit {

  pedido = model.required<Pedido>();

  protected pedidosApi = inject(PedidosApi);

  protected appContent = inject(DsAppContent);

  protected appNotificacao = inject(DsAppNotificacao);

  private appPopup = inject(DsAppPopup);

  protected raizApi = inject(DS_RAIZ_API);

  protected editandoGrupo = signal(false);

  protected editandoSolicitante = signal(false);

  protected editandoAtendente = signal(false);

  protected datasourceAndamentos = new DsDatasourceMemoria();

  protected opcoesUrgencia = TiposUrgenciaPedido;

  private fb = inject(FormBuilder);

  protected formPedido = this.fb.group({
    titulo: this.fb.control('', {validators: [Validators.required, Validators.minLength(10)]}),
    descricao: this.fb.control('', {validators: [Validators.required, Validators.minLength(20)]}),
    urgencia: this.fb.control(2, {validators: [Validators.required], nonNullable: true}),
    grupoAtendimento: this.fb.control<GrupoAtendimento | null>(null, [Validators.required]),
    usuarioSolicitante: this.fb.control<Usuario | null>(null, {validators: [Validators.required]}),
    usuarioAtendente: this.fb.control<Usuario | null>(null),
  });

  protected formAndamento = this.fb.group({
    descricao: this.fb.control('', {validators: [Validators.required, Validators.minLength(20)]}),
    arquivos: this.fb.control<File[]>([], {nonNullable: true}),
  });

  protected formFechar = this.fb.group({
    avaliacao: this.fb.control<number>(4, {nonNullable: true}),
    texto: this.fb.control('', {nonNullable: true, validators: [Validators.maxLength(1000)]}),
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
    if (!this.pedido().id)
      return;

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


  protected async suspender() {
    if (this.pedido().status == 'E' || this.pedido().status == 'F')
      return;

    this.appContent.bloquear();
    this.pedidosApi.suspenderPedido(this.pedido())
      .pipe(finalize(() => this.appContent.desbloquear()))
      .subscribe(pedido => {
        this.pedido.update(p => ({...p, status: pedido.status}));
        this.carregarAndamentos();
      });
  }


  protected async fecharPedido() {
    if (this.pedido().status === 'F')
      return;

    this.appContent.bloquear();
    this.pedidosApi.fecharPedido(this.pedido(), this.formFechar.getRawValue())
      .pipe(finalize(() => this.appContent.desbloquear()))
      .subscribe(pedido => {
        this.pedido.update(p => ({...p, status: pedido.status}));
        this.carregarAndamentos();
      });
  }


  protected salvarAlteracoesPedido() {
    const body = {...this.pedido(), ...this.formPedido.value} as Pedido;

    this.appContent.bloquear();
    this.pedidosApi.alterar(body)
      .pipe(finalize(() => this.appContent.desbloquear()))
      .subscribe(pedido => {
        this.pedido.set(pedido);
        this.atualizarFormComPedidoCarregado();
        this.carregarAndamentos();
      });
  }


  protected registrarAndamento() {
    if (!this.formAndamento.value.descricao)
      return;

    this.appContent.bloquear();
    this.pedidosApi.registrarAndamento(
      this.pedido(),
      this.formAndamento.value.descricao,
      this.formAndamento.value.arquivos)
      .pipe(finalize(() => this.appContent.desbloquear()))
      .subscribe({
        next: () => {
          this.formAndamento.reset();
          this.carregarAndamentos();

          if (this.pedido().status === 'E')
            this.pedido.update(p => ({...p, status: 'A'}));
        },
        error: (error) => this.appNotificacao.notificarErro(error.message)
      });
  }

}
