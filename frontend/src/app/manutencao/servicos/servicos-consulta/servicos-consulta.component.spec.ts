import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServicosConsultaComponent } from './servicos-consulta.component';

describe('ServicosConsultaComponent', () => {
  let component: ServicosConsultaComponent;
  let fixture: ComponentFixture<ServicosConsultaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServicosConsultaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServicosConsultaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
