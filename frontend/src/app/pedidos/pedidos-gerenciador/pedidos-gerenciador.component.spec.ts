import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PedidosGerenciadorComponent } from './pedidos-gerenciador.component';

describe('PedidosGerenciadorComponent', () => {
  let component: PedidosGerenciadorComponent;
  let fixture: ComponentFixture<PedidosGerenciadorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PedidosGerenciadorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PedidosGerenciadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
