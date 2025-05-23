import { ChangeDetectionStrategy, Component, inject, model, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { DS_RAIZ_API } from '@dsmpf/ngx-dsmpf/configuracao';
import { DsConteudoImports } from '@dsmpf/ngx-dsmpf/conteudo';
import { DsCardImports } from '@dsmpf/ngx-dsmpf/conteudo/card';
import { DsDatasourceMemoria } from '@dsmpf/ngx-dsmpf/datasource';
import { DsDatatableColunaDef, DsDatatableImports } from '@dsmpf/ngx-dsmpf/datasource/datatable';
import { DsBotaoComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes';
import { DsBotaoIconeComponent, DsBotaoIconeVerticalComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes/icone';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { DsAutocompletarDirective } from '@dsmpf/ngx-dsmpf/form/autocompletar';
import { DsAppContent } from '@dsmpf/ngx-dsmpf/layout/content';
import { finalize } from 'rxjs';
import { Andamento } from '../../shared/model/andamento';
import { GrupoAtendimento } from '../../shared/model/grupo-atendimento';
import { Pedido } from '../../shared/model/pedido';
import { Usuario } from '../../shared/model/usuario';
import { CorStatusPedidoPipe } from '../cor-status-pedido.pipe';
import { PedidosService } from '../pedidos.service';
import { StatusPedidoPipe } from '../status-pedido.pipe';
import { TiposUrgenciaPedido, UrgenciaPedidoPipe } from '../urgencia-pedido.pipe';
import { DsPreviaArquivoComponent, DsUploadArquivoComponent } from '@dsmpf/ngx-dsmpf/arquivo';

@Component({
  selector: 'app-pedido',
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
    DsPreviaArquivoComponent
  ],
  templateUrl: './pedido.component.html',
  styleUrl: './pedido.component.scss'
})
export class PedidoComponent implements OnInit {

  pedido = model.required<Pedido>();

  protected raizApi = inject(DS_RAIZ_API);

  protected pedidosService = inject(PedidosService);

  private contentService = inject(DsAppContent);

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

  protected formAndamento = this.fb.group({
    descricao: this.fb.control('', {validators: [Validators.required, Validators.minLength(20)]}),
    arquivos: this.fb.control<File[]>([], {nonNullable: true}),
  });

  protected buscarGrupo = this.pedidosService
  .criarFuncaoAutocompletar<GrupoAtendimento>('buscar/grupos');

  protected buscarUsuario = this.pedidosService
    .criarFuncaoAutocompletar<Usuario>('buscar/usuarios');


  protected colunas: DsDatatableColunaDef<Andamento>[] = [
    {id: 'usuario.nome', titulo: 'Usuario', ordenavel: false, largura: '180'},
    {id: 'dataRegistro', titulo: 'Registro', ordenavel: true, largura: '130', formatoData: 'dd/MM/yyyy HH:mm'},
  ];


  ngOnInit() {
    this.atualizarFormComPedidoCarregado();
    this.inicializarDatasourceAndamentos();
  }


  private inicializarDatasourceAndamentos() {
    const endpoint = `${this.pedido().id}/andamentos`;

    this.datasourceAndamentos.definirFonteDados(
      this.pedidosService.obterRecursoAuxiliar(endpoint, {relativo: true})
    );

    this.datasourceAndamentos.conectar();
  }


  protected carregarAndamentos() {
    this.datasourceAndamentos.atualizar();
  }


  protected suspender() {
    if (this.pedido().status !== 'E' && this.pedido().status !== 'F') {
      this.contentService.bloquear();
      this.pedidosService.suspenderPedido(this.pedido())
        .pipe(finalize(() => this.contentService.desbloquear()))
        .subscribe(pedido => {
          this.pedido.update(p => ({...p, status: pedido.status}));
          this.carregarAndamentos();
        });
    }
  }


  protected salvarAlteracoesPedido() {
    const body = {...this.pedido(), ...this.formPedido.value} as Pedido;

    this.contentService.bloquear();
    this.pedidosService.alterar(body)
      .pipe(finalize(() => this.contentService.desbloquear()))
      .subscribe(pedido => {
        this.pedido.set(pedido);
        this.atualizarFormComPedidoCarregado();
        this.carregarAndamentos();
      });
  }


  protected registrarAndamento() {
    if (!this.formAndamento.value.descricao)
      return;

    this.contentService.bloquear();
    this.pedidosService.registrarAndamento(
      this.pedido(),
      this.formAndamento.value.descricao,
      this.formAndamento.value.arquivos)
      .pipe(finalize(() => this.contentService.desbloquear()))
      .subscribe(() => {
        this.formAndamento.reset();
        this.carregarAndamentos();
      });
  }

  protected excluirArquivoUpload(indice: number) {
    if (!this.formAndamento.value.arquivos?.length)
      return;

    const arquivos = this.formAndamento.value.arquivos.filter((_arq, i) => i !== indice);
    this.formAndamento.patchValue({arquivos});
  }

  private atualizarFormComPedidoCarregado() {
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

}
