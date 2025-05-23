import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriasServicosComponent } from './categorias-servicos.component';

describe('CategoriasServicosComponent', () => {
  let component: CategoriasServicosComponent;
  let fixture: ComponentFixture<CategoriasServicosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoriasServicosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoriasServicosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
