import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PainelGerencial } from './painel-gerencial';

describe('PainelGerencial', () => {
  let component: PainelGerencial;
  let fixture: ComponentFixture<PainelGerencial>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PainelGerencial]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PainelGerencial);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
