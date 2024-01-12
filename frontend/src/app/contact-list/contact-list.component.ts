import { Component, OnInit } from '@angular/core';
import { ContactService } from '../service/contact.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { Contact } from '../model/Contact.model';
import { PhoneNumber } from '../model/phoneNumber.model';

@Component({
  selector: 'app-contact-list',
  templateUrl: './contact-list.component.html',
  styleUrls: ['./contact-list.component.scss']
})
export class ContactListComponent implements OnInit {

  contacts: any[] = [];
  contactId : number =0;
  contactForm: FormGroup;
  selectedContact : any ={ phones: [] };
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private contactService: ContactService,
    private router: Router,private fb: FormBuilder) {
      this.contactForm = this.fb.group({
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        street: [''],
        city: [''],
        country: [''],
        zip: [''],
        phones: this.fb.array([]),
      });
  }

  ngOnInit(): void {
    this.getAllContacts();
  }


  initializeForm(): void {

    if (this.selectedContact) {
      this.contactForm.patchValue({
        firstName: this.selectedContact.firstName,
        lastName: this.selectedContact.lastName,
        email: this.selectedContact.email,
        street: this.selectedContact.addresse?.street,
        city: this.selectedContact.addresse?.city,
        country:this.selectedContact.addresse?.country,
        zip: this.selectedContact.addresse?.zip,
      });

      this.clearPhones();

      this.selectedContact.phones.forEach((phone:any) => this.addPhone(phone.idPhoneNumber,phone.phoneKind, phone.phoneNumber));
    }
  }
  redirectToAddContact() {
    this.router.navigateByUrl("add-contact");
  }
  clearPhones(): void {
    const phonesArray = this.contactForm.get('phones') as FormArray;
    while (phonesArray.length !== 0) {
      phonesArray.removeAt(0);
    }
  }

  onSubmit(contactId : number): void {
    const updateContact = this.contactForm.value;

    this.contactService.updateContact(contactId,updateContact)
      .subscribe(response => {
        console.log('Contact modifié avec succès', response);
        this.successMessage = 'Contact modifié avec succès';
        this.errorMessage = '';
        this.contactForm.reset();
        this.findContact(contactId);
      }, error => {
        console.error('Erreur lors de la modification du contact', error);
        this.errorMessage = 'Erreur lors de la modification du contact';
        this.successMessage = '';
      });
  }
  

  get phoneControls() {
    return (this.contactForm.get('phones') as FormArray).controls;
  }

  addPhone(id : number | null = null,phoneKind = '', phoneNumber = ''): void {
    const phonesArray = this.contactForm.get('phones') as FormArray;
    phonesArray.push(this.fb.group({
      idPhoneNumber: [id],
      phoneKind: [phoneKind],
      phoneNumber: [phoneNumber],
    }));
  }
  

  getAllContacts(): void {
    const result = this.contactService.getAllContacts().subscribe(
      (data: any) => {
        this.contacts = data;
        if (this.contacts && this.contacts.length > 0) {
          this.findContact(this.contacts[0].idContact);
        }
      }
    );
  }

  findContact(contactId: number): void {
    this.contactId = contactId;
    this.contactService.getOneContact(contactId).subscribe(
      (contact) => {
        if (contact && contact.phones) {
          this.selectedContact = contact;

          this.initializeForm();
        } else {
          console.error('Le contact ne contient pas les informations attendues.');
        }
      },
      (error) => {
        console.error(error);
      }
    );
  }
  
  deleteContact(contactId : number) : void{
    this.contactService.deleteContact(contactId).subscribe(
      (response) => {
        console.log('Contact supprimé avec succès', response);
        this.getAllContacts();
      },
      (error) => {
        console.error('Erreur lors de la suppression du contact', error);
      }
    );
  }

  deletePhoneNumber(phoneNumberId : any,contactId : number) : void{

    this.contactService.deletePhone(phoneNumberId).subscribe(
      (response) => {
        console.log('Téléphone supprimé avec succès', response);
        this.findContact(contactId);
      },
      (error) => {
        console.error('Erreur lors de la suppression du téléphone', error);
      }
    );
  }

}
