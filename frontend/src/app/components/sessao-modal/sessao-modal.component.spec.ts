import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SessaoModalComponent } from './sessao-modal.component';

describe('SessaoModalComponent', () => {
  let component: SessaoModalComponent;
  let fixture: ComponentFixture<SessaoModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SessaoModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SessaoModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
