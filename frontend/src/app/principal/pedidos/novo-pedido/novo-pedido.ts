import { ChangeDetectionStrategy, Component, inject, input, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DsAppNotificacao } from '@dsmpf/ngx-dsmpf/basico';
import { DsConteudoImports } from '@dsmpf/ngx-dsmpf/conteudo';
import { DsCardImports } from '@dsmpf/ngx-dsmpf/conteudo/card';
import { DsBotaoComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { DsAutocompletarDirective } from '@dsmpf/ngx-dsmpf/form/autocompletar';
import { DsAppContent } from '@dsmpf/ngx-dsmpf/layout/content';
import { finalize } from 'rxjs';
import { GrupoAtendimento } from '../../../shared/model/grupo-atendimento';
import { Pedido } from '../../../shared/model/pedido';
import { Usuario } from '../../../shared/model/usuario';
import { TiposUrgenciaPedido, UrgenciaPedidoPipe } from '../../../shared/pipes/urgencia-pedido.pipe';
import { PedidosApi } from '../pedidos-api';

@Component({
  selector: 'app-novo-pedido',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    ReactiveFormsModule,
    DsConteudoImports,
    DsCardImports,
    DsFormImports,
    DsBotaoComponent,
    DsAutocompletarDirective,
    UrgenciaPedidoPipe
  ],
  templateUrl: './novo-pedido.html',
  styleUrl: './novo-pedido.scss'
})
export class NovoPedido implements OnInit {

  novoPedido = input<Pedido>(); // obtido do resolve da rota

  private pedidosApi = inject(PedidosApi);

  private appContent = inject(DsAppContent);

  private appNotificacao = inject(DsAppNotificacao);

  private router = inject(Router);

  protected opcoesUrgencia = TiposUrgenciaPedido;

  private fb = inject(FormBuilder);

  protected formPedido = this.fb.group({
    titulo: this.fb.control('', {validators: [Validators.required, Validators.minLength(10)]}),
    descricao: this.fb.control('', {validators: [Validators.required, Validators.minLength(20)]}),
    urgencia: this.fb.control(2, {validators: [Validators.required], nonNullable: true}),
    grupoAtendimento: this.fb.control<GrupoAtendimento | null>(null),
    usuarioSolicitante: this.fb.control<Usuario | null>(null, {validators: [Validators.required]}),
  });

  protected buscarUsuario = this.pedidosApi
    .criarFuncaoAutocompletar<Usuario>('buscar/usuarios');

  protected buscarGrupo = this.pedidosApi
    .criarFuncaoAutocompletar<GrupoAtendimento>('buscar/grupos', {
      parametrosExtras: () => ({ idCategoria: this.novoPedido()?.servico.categoria.id ?? '' })
    });


  ngOnInit() {
    this.formPedido.patchValue(this.novoPedido() ?? {});
  }


  registrar() {
    const pedido = {...this.novoPedido(), ...this.formPedido.value} as Pedido;
    this.appContent.bloquear();

    this.pedidosApi.incluir(pedido)
      .pipe(finalize(() => this.appContent.desbloquear()))
      .subscribe({
        next: pedido => this.router.navigateByUrl(`/pedidos/${pedido.id}`),
        error: error => this.appNotificacao.notificarErro(error.message)
      });
  }

}
