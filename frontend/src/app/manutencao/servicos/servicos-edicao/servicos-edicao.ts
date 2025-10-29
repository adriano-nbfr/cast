import { Component, input } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { DsCardImports } from '@dsmpf/ngx-dsmpf/conteudo/card';
import { DsCrudImports } from '@dsmpf/ngx-dsmpf/crud';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { DsFormSwitchDirective } from '@dsmpf/ngx-dsmpf/form/switch';
import { Categoria } from '../../../shared/model/categoria';
import { Servico } from '../../../shared/model/servico';

@Component({
  selector: 'app-servicos-edicao',
  imports: [
    ReactiveFormsModule,
    DsCardImports,
    DsFormImports,
    DsCrudImports,
    DsFormSwitchDirective
  ],
  templateUrl: './servicos-edicao.html',
  styleUrl: './servicos-edicao.scss'
})
export class ServicosEdicao {

  listagemCategorias = input<Categoria[]>([]); // obtém do resolve da rota

  private fb = new FormBuilder();

  protected formServicos = this.fb.group({
    'nome': this.fb.control<string>('', {
      validators: [Validators.required, Validators.minLength(5), Validators.maxLength(100)]
    }),
    'descricao': this.fb.control<string>('', {
      validators: [Validators.minLength(10), Validators.maxLength(200)]
    }),
    'ativo': this.fb.control<boolean>(true, {nonNullable: true}),
    'idCategoria': this.fb.control<number | null>(null, {
      validators: [Validators.required]
    })
  });


  protected tratamentoCarregar = (servico: Servico) => {
    return {
      ...servico,
      idCategoria: servico.categoria?.id
    };
  };


  protected tratamentoSalvar = (valor: any) => {
    const categoria = this.listagemCategorias().find(c => c.id === valor.idCategoria);
    delete valor.idCategoria;

    return {...valor, categoria} as Servico;
  };

}
