import { Component, input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DsCrudImports } from '@dsmpf/ngx-dsmpf/crud';
import { DsDatatableColunaDef } from '@dsmpf/ngx-dsmpf/datasource/datatable';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { Servico } from '../../../shared/model/servico';
import { Categoria } from '../../../shared/model/categoria';

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

  listagemCategorias = input<Categoria[]>([]) // está obtendo do resolve da configuração do Crud

  protected colunas: DsDatatableColunaDef<Servico>[] = [
    { id: 'categoria.nome', ordenavel: true, titulo: 'Categoria', largura: '30%' },
    { id: 'nome', ordenavel: true, titulo: 'Nome' },
    { id: 'ativo', ordenavel: true, titulo: 'Ativo', largura: '100', traducaoBoolean: {valorTrue: 'Sim', valorFalse: 'Não'} }
  ];

}
