import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GraficoLinhaTempoComponent } from './grafico-linha-tempo.component';

describe('GraficoLinhaTempoComponent', () => {
  let component: GraficoLinhaTempoComponent;
  let fixture: ComponentFixture<GraficoLinhaTempoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GraficoLinhaTempoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GraficoLinhaTempoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
