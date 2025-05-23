import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServicosEdicaoComponent } from './servicos-edicao.component';

describe('ServicosEdicaoComponent', () => {
  let component: ServicosEdicaoComponent;
  let fixture: ComponentFixture<ServicosEdicaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServicosEdicaoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServicosEdicaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
