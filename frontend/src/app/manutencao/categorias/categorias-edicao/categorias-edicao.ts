import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { DsCrudImports } from '@dsmpf/ngx-dsmpf/crud';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { DsFormSwitchDirective } from '@dsmpf/ngx-dsmpf/form/switch';

@Component({
  selector: 'app-categorias-edicao',
  imports: [
    ReactiveFormsModule,
    DsFormImports,
    DsCrudImports,
    DsFormSwitchDirective
  ],
  templateUrl: './categorias-edicao.html',
  styleUrl: './categorias-edicao.scss'
})
export class CategoriasEdicao {

  private fb = new FormBuilder();

  protected formCategoria = this.fb.group({
    'nome': this.fb.control<string>('', {
      validators: [Validators.required, Validators.minLength(5), Validators.maxLength(100)]
    }),
    'descricao': this.fb.control<string>('', {
      validators: [Validators.minLength(10), Validators.maxLength(200)]
    }),
    'ativo': this.fb.control<boolean>(true, {nonNullable: true}),
  });

}
