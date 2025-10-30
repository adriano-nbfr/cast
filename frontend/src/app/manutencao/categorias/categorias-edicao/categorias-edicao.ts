import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { DsCrudImports } from '@dsmpf/ngx-dsmpf/crud';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { CategoriasServicos } from "./categorias-servicos/categorias-servicos";

@Component({
  selector: 'app-categorias-edicao',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    ReactiveFormsModule,
    DsFormImports,
    DsCrudImports,
    CategoriasServicos
],
  templateUrl: './categorias-edicao.html',
  styleUrl: './categorias-edicao.scss'
})
export class CategoriasEdicao {

  private fb = inject(FormBuilder);

  protected formCategoria = this.fb.group({
    'nome': this.fb.control<string>('',
      {validators: [Validators.required, Validators.minLength(5), Validators.maxLength(100)]}
    ),
    'descricao': this.fb.control<string>('',
      {validators: [Validators.minLength(10), Validators.maxLength(200)]}
    ),
    'ativo': this.fb.control<boolean>(false, {nonNullable: true}),
  });

}
