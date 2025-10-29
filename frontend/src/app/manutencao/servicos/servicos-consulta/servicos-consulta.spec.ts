import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServicosConsulta } from './servicos-consulta';

describe('ServicosConsulta', () => {
  let component: ServicosConsulta;
  let fixture: ComponentFixture<ServicosConsulta>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServicosConsulta]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServicosConsulta);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
