import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactGroupeListComponent } from './contact-groupe-list.component';

describe('ContactGroupeListComponent', () => {
  let component: ContactGroupeListComponent;
  let fixture: ComponentFixture<ContactGroupeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContactGroupeListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContactGroupeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
