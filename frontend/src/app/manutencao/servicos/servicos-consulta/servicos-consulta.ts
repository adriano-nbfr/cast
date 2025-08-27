import { Component, inject, input, viewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DsCrudConsultaComponent, DsCrudImports, DsCrudPadraoService } from '@dsmpf/ngx-dsmpf/crud';
import { DsDatatableAcaoDef, DsDatatableColunaDef, DsDatatableImports } from '@dsmpf/ngx-dsmpf/datasource/datatable';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { Servico } from '../../../shared/model/servico';
import { Categoria } from '../../../shared/model/categoria';
import { DsFormSwitchDirective } from '@dsmpf/ngx-dsmpf/form/switch';

@Component({
  selector: 'app-servicos-consulta',
  imports: [
    FormsModule,
    DsCrudImports,
    DsFormImports,
    DsDatatableImports,
    DsFormSwitchDirective
  ],
  templateUrl: './servicos-consulta.html',
  styleUrl: './servicos-consulta.scss'
})
export class ServicosConsulta {

  listagemCategorias = input<Categoria[]>([]) // está obtendo do resolve da configuração do Crud

  protected crudApi = inject(DsCrudPadraoService<Servico>);

  protected crudConsulta = viewChild.required(DsCrudConsultaComponent<Servico>);

  protected colunas: DsDatatableColunaDef<Servico>[] = [
    { id: 'categoria.nome', ordenavel: true, titulo: 'Categoria', largura: '30%' },
    { id: 'nome', ordenavel: true, titulo: 'Nome' },
    // { id: 'ativo', ordenavel: true, titulo: 'Ativo', largura: '100', traducaoBoolean: {valorTrue: 'Sim', valorFalse: 'Não'} }
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
      descricao: 'Desativar o serviço',
      icone: 'la-ban',
      cor: 'danger',
      recarregar: true,
      funcao: (servico) => this.alterarStatusServico(servico, false),
      ocultar: (servico) => !servico.ativo
    },
  ];


  protected inverterStatusServico(servico: Servico, event: Event) {
    event.preventDefault();

    this.crudConsulta().datatable().processarAcaoAvulsa({
      funcao: () => this.alterarStatusServico(servico, !servico.ativo),
      recarregar: true
    });
  }

  private alterarStatusServico(servico: Servico, ativo: boolean) {
    return this.crudApi.alterar({...servico, ativo});
  }

}
