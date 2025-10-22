import { TestBed } from '@angular/core/testing';

import { PedidosApi } from './pedidos-api';

describe('PedidosApi', () => {
  let service: PedidosApi;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PedidosApi);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
