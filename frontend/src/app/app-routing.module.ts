import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddContactComponent } from './add-contact/add-contact.component';
import { ContactListComponent } from './contact-list/contact-list.component';
import { ContactGroupeListComponent } from './contact-groupe-list/contact-groupe-list.component';
import { ContactGroupComponent } from './contact-group/contact-group.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'contact', component: ContactListComponent },
  { path: 'add-contact', component: AddContactComponent },
  { path: 'contact-group-list', component: ContactGroupeListComponent },
  { path: 'contact-group/:data', component: ContactGroupComponent }
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
