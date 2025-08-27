import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriasConsulta } from './categorias-consulta';

describe('CategoriasConsulta', () => {
  let component: CategoriasConsulta;
  let fixture: ComponentFixture<CategoriasConsulta>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoriasConsulta]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoriasConsulta);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
