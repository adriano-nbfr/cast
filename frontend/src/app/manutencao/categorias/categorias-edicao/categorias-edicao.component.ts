import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { DsCrudImports } from '@dsmpf/ngx-dsmpf/crud';
import { DsFormImports } from '@dsmpf/ngx-dsmpf/form';
import { DsFormSwitchDirective } from '@dsmpf/ngx-dsmpf/form/switch';
import { CategoriasServicosComponent } from './categorias-servicos/categorias-servicos.component';

@Component({
  selector: 'app-categorias-edicao',
  imports: [
    ReactiveFormsModule,
    DsCrudImports,
    DsFormImports,
    DsFormSwitchDirective,
    CategoriasServicosComponent
  ],
  templateUrl: './categorias-edicao.component.html',
  styleUrl: './categorias-edicao.component.scss'
})
export class CategoriasEdicaoComponent {

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
