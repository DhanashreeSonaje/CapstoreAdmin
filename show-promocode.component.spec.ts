import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowPromocodeComponent } from './show-promocode.component';

describe('ShowPromocodeComponent', () => {
  let component: ShowPromocodeComponent;
  let fixture: ComponentFixture<ShowPromocodeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowPromocodeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowPromocodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
