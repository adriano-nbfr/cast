import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriasEdicaoComponent } from './categorias-edicao.component';

describe('CategoriasEdicaoComponent', () => {
  let component: CategoriasEdicaoComponent;
  let fixture: ComponentFixture<CategoriasEdicaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoriasEdicaoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoriasEdicaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
