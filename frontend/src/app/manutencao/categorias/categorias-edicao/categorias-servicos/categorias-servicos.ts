import { Component, inject, input } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { DsCrudImports, DsCrudTextoRecurso } from '@dsmpf/ngx-dsmpf/crud';
import { DsDatatableColunaDef, DsDatatableImports } from '@dsmpf/ngx-dsmpf/datasource/datatable';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { DsFormSwitchDirective } from '@dsmpf/ngx-dsmpf/form/switch';
import { Servico } from '../../../../shared/model/servico';
import { Categoria } from '../../../../shared/model/categoria';

@Component({
  selector: 'app-categorias-servicos',
  imports: [
    ReactiveFormsModule,
    DsFormImports,
    DsCrudImports,
    DsDatatableImports,
    DsFormSwitchDirective
  ],
  templateUrl: './categorias-servicos.html',
  styleUrl: './categorias-servicos.scss'
})
export class CategoriasServicos {

  categoria = input.required<Categoria>();

  private fb = inject(FormBuilder);

  protected formServico = this.fb.group({
    'nome': this.fb.control<string>('', {
      validators: [Validators.required, Validators.minLength(5), Validators.maxLength(100)]
    }),
    'ativo': this.fb.control<boolean>(true, {nonNullable: true})
  });


  protected textos: DsCrudTextoRecurso = {
    nomeSingular: 'Serviço',
    nomePlural: 'Serviços',
    artigo: 'o'
  };

  protected colunas: DsDatatableColunaDef<Servico>[] = [
    { id: 'nome', ordenavel: true, titulo: 'Nome' },
    {
      id: 'ativo',
      ordenavel: true,
      titulo: 'Ativo',
      largura: '100',
      traducaoBoolean: {valorTrue: 'Sim', valorFalse: 'Não'}
    }
  ];


  protected tratamentoSalvar = (valor: any) => {
    const categoria = this.categoria();
    return {...valor, categoria} as Servico;
  };

}
