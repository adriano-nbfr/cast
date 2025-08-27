import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServicosEdicao } from './servicos-edicao';

describe('ServicosEdicao', () => {
  let component: ServicosEdicao;
  let fixture: ComponentFixture<ServicosEdicao>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServicosEdicao]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServicosEdicao);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
