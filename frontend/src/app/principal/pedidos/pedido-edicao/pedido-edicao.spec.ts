import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PedidoEdicao } from './pedido-edicao';

describe('PedidoEdicao', () => {
  let component: PedidoEdicao;
  let fixture: ComponentFixture<PedidoEdicao>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PedidoEdicao]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PedidoEdicao);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
