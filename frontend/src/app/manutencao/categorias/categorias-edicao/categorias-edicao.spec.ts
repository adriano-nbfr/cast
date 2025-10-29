import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriasEdicao } from './categorias-edicao';

describe('CategoriasEdicao', () => {
  let component: CategoriasEdicao;
  let fixture: ComponentFixture<CategoriasEdicao>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoriasEdicao]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoriasEdicao);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
