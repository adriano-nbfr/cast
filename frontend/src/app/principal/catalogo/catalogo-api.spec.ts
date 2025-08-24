import { TestBed } from '@angular/core/testing';

import { CatalogoApi } from './catalogo-api';

describe('CatalogoApi', () => {
  let service: CatalogoApi;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CatalogoApi);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
