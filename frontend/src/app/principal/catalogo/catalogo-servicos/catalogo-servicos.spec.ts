import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatalogoServicos } from './catalogo-servicos';

describe('CatalogoServicos', () => {
  let component: CatalogoServicos;
  let fixture: ComponentFixture<CatalogoServicos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CatalogoServicos]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CatalogoServicos);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
