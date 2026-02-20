import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VotosModalComponent } from './votos-modal.component';

describe('VotosModalComponent', () => {
  let component: VotosModalComponent;
  let fixture: ComponentFixture<VotosModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VotosModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VotosModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
