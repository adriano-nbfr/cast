import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { DS_RAIZ_API } from '@dsmpf/ngx-dsmpf/configuracao';
import { Categoria } from '../shared/model/categoria';
import { Servico } from '../shared/model/servico';

@Injectable({
  providedIn: 'root'
})
export class CatalogoService {

  private raizApi = inject(DS_RAIZ_API);
  private http = inject(HttpClient);

  readonly endpointBaseCategorias = `${this.raizApi}/catalogo/categorias`;

  carregarCategorias() {
    return this.http.get<Categoria[]>(this.endpointBaseCategorias);
  }

  obterCategoria(idCategoria: number) {
    return this.http.get<Categoria>(`${this.endpointBaseCategorias}/${idCategoria}`);
  }

  carregarServicos(idCategoria: number) {
    const endpointServicos = `${this.endpointBaseCategorias}/${idCategoria}/servicos`;
    return this.http.get<Servico[]>(endpointServicos);
  }
}
