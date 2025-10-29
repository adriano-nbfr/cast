import { Component, computed, inject, input } from '@angular/core';
import { DsDatatableColunaDef } from '@dsmpf/ngx-dsmpf/datasource/datatable';
import { Servico } from '../../../shared/model/servico';
import { FormsModule } from '@angular/forms';
import { DsCrudImports } from '@dsmpf/ngx-dsmpf/crud';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { Categoria } from '../../../shared/model/categoria';
import { DsAppSeguranca } from '@dsmpf/ngx-dsmpf/seguranca';
import { environment } from '../../../../environment';

@Component({
  selector: 'app-servicos-consulta',
  imports: [
    FormsModule,
    DsCrudImports,
    DsFormImports
  ],
  templateUrl: './servicos-consulta.html',
  styleUrl: './servicos-consulta.scss'
})
export class ServicosConsulta {

  listagemCategorias = input<Categoria[]>([]); // obtém do resolve da rota

  private appSeguranca = inject(DsAppSeguranca);

  protected isGerente = computed(() => this.appSeguranca.isUsuarioAutorizado(environment.papeis.PAPEL_GERENTE));


  protected colunas: DsDatatableColunaDef<Servico>[] = [
    { id: 'categoria.nome', ordenavel: true, titulo: 'Categoria', largura: '30%' },
    { id: 'nome', ordenavel: true, titulo: 'Nome' },
    {
      id: 'ativo',
      ordenavel: true,
      titulo: 'Ativa',
      largura: '100',
      traducaoBoolean: {valorTrue: 'Sim', valorFalse: 'Não'}
    }
  ];

}
