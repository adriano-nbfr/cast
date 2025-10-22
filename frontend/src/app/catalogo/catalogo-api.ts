import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { DS_RAIZ_API } from '@dsmpf/ngx-dsmpf/configuracao';
import { Categoria } from '../shared/model/categoria';

@Injectable({
  providedIn: 'root'
})
export class CatalogoApi {

  private raizApi = inject(DS_RAIZ_API);

  private http = inject(HttpClient);

  readonly endpointBaseCategorias = `${this.raizApi}/catalogo/categorias`;


  carregarCategorias() {
    return this.http.get<Categoria[]>(this.endpointBaseCategorias);
  }

}
