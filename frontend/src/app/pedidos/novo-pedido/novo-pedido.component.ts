import { Component, inject, input, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DsConteudoImports } from '@dsmpf/ngx-dsmpf/conteudo';
import { DsCardImports } from '@dsmpf/ngx-dsmpf/conteudo/card';
import { DsDatatableImports } from '@dsmpf/ngx-dsmpf/datasource/datatable';
import { DsBotaoComponent } from '@dsmpf/ngx-dsmpf/elementos/botoes';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { DsAutocompletarDirective } from '@dsmpf/ngx-dsmpf/form/autocompletar';
import { DsAppContent } from '@dsmpf/ngx-dsmpf/layout/content';
import { finalize } from 'rxjs';
import { GrupoAtendimento } from '../../shared/model/grupo-atendimento';
import { Pedido } from '../../shared/model/pedido';
import { Usuario } from '../../shared/model/usuario';
import { PedidosService } from '../pedidos.service';
import { TiposUrgenciaPedido, UrgenciaPedidoPipe } from '../urgencia-pedido.pipe';

@Component({
  selector: 'app-novo-pedido',
  imports: [
    ReactiveFormsModule,
    DsConteudoImports,
    DsDatatableImports,
    DsCardImports,
    DsFormImports,
    UrgenciaPedidoPipe,
    DsBotaoComponent,
    DsAutocompletarDirective
  ],
  templateUrl: './novo-pedido.component.html',
  styleUrl: './novo-pedido.component.scss'
})
export class NovoPedidoComponent implements OnInit {

  novoPedido = input.required<Pedido>(); // obtido do resolve

  private pedidosService = inject(PedidosService);

  private contentService = inject(DsAppContent);

  private router = inject(Router);

  protected opcoesUrgencia = TiposUrgenciaPedido;

  private fb = new FormBuilder();

  protected formPedido = this.fb.group({
    titulo: this.fb.control('', {validators: [Validators.required, Validators.minLength(10)]}),
    descricao: this.fb.control('', {validators: [Validators.required, Validators.minLength(50)]}),
    urgencia: this.fb.control(2, {validators: [Validators.required], nonNullable: true}),
    grupoAtendimento: this.fb.control<GrupoAtendimento | null>(null),
    usuarioSolicitante: this.fb.control<Usuario | null>(null, {validators: [Validators.required]}),
  });


  protected buscarUsuario = this.pedidosService
    .criarFuncaoAutocompletar<Usuario>('buscar/usuarios');


  protected buscarGrupo = this.pedidosService
    .criarFuncaoAutocompletar<GrupoAtendimento>('buscar/grupos', {
      parametrosExtras: () => ({ idCategoria: this.novoPedido().servico.categoria.id ?? '' })
    });


  ngOnInit() {
    this.formPedido.patchValue(this.novoPedido());
  }


  registrar() {
    const pedido = {...this.novoPedido(), ...this.formPedido.value} as Pedido;
    this.contentService.bloquear();

    this.pedidosService.incluir(pedido)
      .pipe(finalize(() => this.contentService.desbloquear()))
      .subscribe(pedido => this.router.navigateByUrl(`/pedidos/${pedido.id}`));
  }


}
