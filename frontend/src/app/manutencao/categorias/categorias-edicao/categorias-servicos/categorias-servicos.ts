import { ChangeDetectionStrategy, Component, inject, input, viewChild } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { DsCrudEdicaoDetalheComponent, DsCrudImports, DsCrudTextoRecurso } from '@dsmpf/ngx-dsmpf/crud';
import { DsDatatableAcaoDef, DsDatatableColunaDef, DsDatatableImports } from '@dsmpf/ngx-dsmpf/datasource/datatable';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { DsFormSwitchDirective } from '@dsmpf/ngx-dsmpf/form/switch';
import { Categoria } from '../../../../shared/model/categoria';
import { Servico } from '../../../../shared/model/servico';

@Component({
  selector: 'app-categorias-servicos',
  changeDetection: ChangeDetectionStrategy.OnPush,
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

  private crudDetalhe = viewChild.required(DsCrudEdicaoDetalheComponent<Servico>);


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
    // {
    //   id: 'ativo',
    //   ordenavel: true,
    //   titulo: 'Ativo',
    //   largura: '100',
    //   traducaoBoolean: {valorTrue: 'Sim', valorFalse: 'Não'}
    // }
  ];

  protected acoesDatatable: DsDatatableAcaoDef<Servico>[] = [
    {
      id: 'ativar',
      descricao: 'Ativar o serviço',
      icone: 'la-check',
      cor: 'success',
      recarregar: true,
      funcao: (servico) => this.alterarStatusServico(servico, true),
      ocultar: (servico) => servico.ativo
    },
    {
      id: 'desativar',
      descricao: 'Destivar o serviço',
      icone: 'la-ban',
      cor: 'danger',
      recarregar: true,
      funcao: (servico) => this.alterarStatusServico(servico, false),
      ocultar: (servico) => !servico.ativo
    }
  ];


  protected tratamentoSalvar = (valor: any) => {
    const categoria = this.categoria();
    return {...valor, categoria} as Servico;
  };


  protected inverterStatusServico(servico: Servico, event: Event) {
    event.preventDefault();

    this.crudDetalhe().datatable().processarAcaoAvulsa({
      funcao: () => this.alterarStatusServico(servico, !servico.ativo),
      recarregar: true
    });
  }

  private alterarStatusServico(servico: Servico, ativo: boolean) {
    return this.crudDetalhe().service.alterar({...servico, ativo});
  }

}
