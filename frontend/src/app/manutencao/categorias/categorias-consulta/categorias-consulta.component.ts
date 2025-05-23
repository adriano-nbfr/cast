import { Component, computed, inject } from '@angular/core';
import { DsDatatableColunaDef } from '@dsmpf/ngx-dsmpf/datasource/datatable';
import { Categoria } from '../../../shared/model/categoria';
import { DsCrudImports } from '@dsmpf/ngx-dsmpf/crud';
import { FormsModule } from '@angular/forms';
import { DsAppSeguranca } from '@dsmpf/ngx-dsmpf/seguranca';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';

@Component({
  selector: 'app-categorias-consulta',
  imports: [
    FormsModule,
    DsCrudImports,
    DsFormImports
  ],
  templateUrl: './categorias-consulta.component.html',
  styleUrl: './categorias-consulta.component.scss'
})
export class CategoriasConsultaComponent {

  private appSeguranca = inject(DsAppSeguranca);

  protected isAdmin = computed(() => this.appSeguranca.isAdmin());

  protected colunas: DsDatatableColunaDef<Categoria>[] = [
    {id: 'nome', ordenavel: true, titulo: 'Nome'},
    {
      id: 'ativo',
      ordenavel: true,
      titulo: 'Ativa',
      largura: '100',
      traducaoBoolean: {valorTrue: 'Sim', valorFalse: 'Não'}
    },
  ];


  protected exclusaoVisivel = (categoria: Categoria) => ((categoria.id ?? 0) as number) % 2 == 0;

}
