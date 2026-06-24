import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminGames } from './admin-games';

describe('AdminGames', () => {
  let component: AdminGames;
  let fixture: ComponentFixture<AdminGames>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AdminGames],
    }).compileComponents();

    fixture = TestBed.createComponent(AdminGames);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
