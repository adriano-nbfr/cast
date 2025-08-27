import { Component, input, viewChild } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { DsCrudEdicaoDetalheComponent, DsCrudImports, DsCrudTextoRecurso } from '@dsmpf/ngx-dsmpf/crud';
import { DsDatatableAcaoDef, DsDatatableColunaDef } from '@dsmpf/ngx-dsmpf/datasource/datatable';
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
    DsFormSwitchDirective
  ],
  templateUrl: './categorias-servicos.html',
  styleUrl: './categorias-servicos.scss'
})
export class CategoriasServicos {

  categoria = input.required<Categoria>();

  protected crudDetalhe = viewChild.required(DsCrudEdicaoDetalheComponent<Servico>);

  protected textos: DsCrudTextoRecurso = {
    nomeSingular: 'Serviço',
    nomePlural: 'Serviços',
    artigo: 'o'
  };

  private fb = new FormBuilder();

  protected formServico = this.fb.group({
    'nome': this.fb.control<string>('', {
      validators: [Validators.required, Validators.minLength(5), Validators.maxLength(100)]
    }),
    'ativo': this.fb.control<boolean>(true, {nonNullable: true})
  });


  protected colunas : DsDatatableColunaDef<Servico>[] = [
    {id: 'nome', ordenavel: true, titulo: 'Nome'},
    {id: 'ativo', ordenavel: true, titulo: 'Ativo', largura: '100', traducaoBoolean: {valorFalse: 'Não', valorTrue: 'Sim'}}
  ];


  protected acoesDatatable: DsDatatableAcaoDef<Servico>[] = [
    {
      id: 'ativar',
      descricao: 'Ativar o serviço',
      icone: 'la-check',
      cor: 'success',
      recarregar: true,
      funcao: servico => this.alterarStatusServico(servico, true),
      ocultar: servico => servico.ativo,
    },
    {
      id: 'desativar',
      descricao: 'Desativar o serviço',
      icone: 'la-ban',
      cor: 'danger',
      recarregar: true,
      funcao: servico => this.alterarStatusServico(servico, false),
      ocultar: servico => !servico.ativo
    }
  ];

  private alterarStatusServico(servico: Servico, ativo: boolean) {
    return this.crudDetalhe().service.alterar({...servico, ativo});
  }


  protected tratamentoSalvar = (valor: any) => {
    const servico: Servico = {
      ...valor,
      categoria: this.categoria()
    };

    return servico;
  }

}
