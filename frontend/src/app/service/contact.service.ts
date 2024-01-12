// contact.service.ts

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Contact } from '../model/Contact.model';

@Injectable({
  providedIn: 'root'
})
export class ContactService {
  private apiUrl = 'http://localhost:8080/contacts';

  constructor(private http: HttpClient) {}

  addContact(contact: any): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json', 
      })
    }

    return this.http.post<any>(`${this.apiUrl}/addContact`, contact,httpOptions);

  }

  getAllContacts(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/all`);
  }

  getOneContact(contactId: number): Observable<Contact> {
    const url = `${this.apiUrl}/contact/${contactId}`;
    return this.http.get<Contact>(url);
  }

  deleteContact(contactId : number) : Observable<string>{
    const url = `${this.apiUrl}/${contactId}`;
    return this.http.delete<any>(url);
  }

  deletePhone(PhoneNumberId : number) : Observable<string>{
    const url = `${this.apiUrl}/phoneNumber/${PhoneNumberId}`;
    return this.http.delete<any>(url);
  } 

  updateContact(contactId: number, contact : any): Observable<string> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json', 
      })
    }
    const url = `${this.apiUrl}/update/${contactId}`;
    return this.http.put<any>(url, contact,httpOptions);
  }

}
