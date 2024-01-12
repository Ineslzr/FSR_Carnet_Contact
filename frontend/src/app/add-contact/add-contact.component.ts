import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { ContactService } from '../service/contact.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-contact',
  templateUrl: './add-contact.component.html',
  styleUrls: ['./add-contact.component.scss']
})
export class AddContactComponent implements OnInit {
  contactForm!: FormGroup;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private formBuilder: FormBuilder, 
    private contactService: ContactService,
    private router: Router) {}

  ngOnInit() {
    this.initForm();
  }

  initForm() {
    this.contactForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      street: [''],
      city: [''],
      zip: [''],
      country: [''],
      phones: this.formBuilder.array([])
    });
  }

  get phoneForms() {
    return this.contactForm.get('phones') as FormArray;
  }

  get phoneControls() {
    return (this.contactForm.get('phones') as FormArray).controls;
  }

  addPhone() {
    const phone = this.formBuilder.group({
      phoneKind: [''],
      phoneNumber: ['']
    });

    this.phoneForms.push(phone);
  }

  onSubmit() {
    const newContact = this.contactForm.value;
  
    const result = this.contactService.addContact(newContact).subscribe(
      (response : any) => {
        this.successMessage = 'Contact ajouté avec succès';
        this.errorMessage = '';
        this.contactForm.reset();
        this.router.navigate(['/contact']);
      },
      (error: any) => {
        this.successMessage = '';
        this.errorMessage = error.error;
      }

    );
  }
}
