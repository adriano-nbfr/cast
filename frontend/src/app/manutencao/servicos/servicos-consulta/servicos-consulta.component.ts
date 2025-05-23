import { Component, input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DsCrudConsultaComponent } from '@dsmpf/ngx-dsmpf/crud';
import { DsDatatableColunaDef, DsDatatableImports } from '@dsmpf/ngx-dsmpf/datasource/datatable';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { Categoria } from '../../../shared/model/categoria';
import { Servico } from '../../../shared/model/servico';

@Component({
  selector: 'app-servicos-consulta',
  imports: [
    FormsModule,
    DsCrudConsultaComponent,
    DsFormImports,
    DsDatatableImports,
  ],
  templateUrl: './servicos-consulta.component.html',
  styleUrl: './servicos-consulta.component.scss'
})
export class ServicosConsultaComponent {

  listagemCategorias = input<Categoria[]>([]); // está obtendo diretamente do configurado na rota

  protected colunas: DsDatatableColunaDef<Servico>[] = [
    { id: 'categoria.nome', ordenavel: true, titulo: 'Categoria', largura: '30%' },
    { id: 'nome', ordenavel: true, titulo: 'Nome', conteudoLimitado: '1-linha' },
    { id: 'ativo', ordenavel: true, titulo: 'Ativo', traducaoBoolean: {valorTrue: 'Sim', valorFalse: 'Não'} },
  ];


}
