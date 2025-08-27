import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriasServicos } from './categorias-servicos';

describe('CategoriasServicos', () => {
  let component: CategoriasServicos;
  let fixture: ComponentFixture<CategoriasServicos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoriasServicos]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoriasServicos);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
