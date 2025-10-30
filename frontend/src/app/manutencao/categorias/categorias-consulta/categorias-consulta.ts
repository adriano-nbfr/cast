import { ChangeDetectionStrategy, Component, computed, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DsCrudImports } from '@dsmpf/ngx-dsmpf/crud';
import { DsDatatableColunaDef } from '@dsmpf/ngx-dsmpf/datasource/datatable';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { DsAppSeguranca } from '@dsmpf/ngx-dsmpf/seguranca';
import { Categoria } from '../../../shared/model/categoria';

@Component({
  selector: 'app-categorias-consulta',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    FormsModule,
    DsFormImports,
    DsCrudImports
  ],
  templateUrl: './categorias-consulta.html',
  styleUrl: './categorias-consulta.scss'
})
export class CategoriasConsulta {

  private appSeguranca = inject(DsAppSeguranca);

  protected isAdmin = computed(() => this.appSeguranca.isAdmin());


  protected colunas: DsDatatableColunaDef<Categoria>[] = [
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
