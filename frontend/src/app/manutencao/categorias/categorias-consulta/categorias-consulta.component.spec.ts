import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriasConsultaComponent } from './categorias-consulta.component';

describe('CategoriasConsultaComponent', () => {
  let component: CategoriasConsultaComponent;
  let fixture: ComponentFixture<CategoriasConsultaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoriasConsultaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoriasConsultaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
