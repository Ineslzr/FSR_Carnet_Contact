import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
  })
export class ContactGroupeService {
    private apiUrl = 'http://localhost:8080/contactGroup';

    constructor(private http: HttpClient) {}

    createContactGroup(contactGroup: any): Observable<any> {
        const httpOptions = {
            headers: new HttpHeaders({
              'Content-Type': 'application/json'
            })
          };
        return this.http.post(`${this.apiUrl}/create`, contactGroup,httpOptions);
    }

    getAllContactGroups(): Observable<any[]>{
        return this.http.get<any[]>(`${this.apiUrl}/all`);
    }

    getAContactGroup(contactGroupId: number):Observable<any[]> {
      return this.http.get<any[]>(`${this.apiUrl}/group/${contactGroupId}`);
    }

    updateContactGroup(contactGroupId: number, group:any):Observable<any[]> {
      const url = `${this.apiUrl}/update/${contactGroupId}`;
      return this.http.put<any>(url, group);
    }

    addContacts(contactGroupId: number, contacts: any[]):Observable<any[]>{
      const url = `${this.apiUrl}/addContact/${contactGroupId}`;
      return this.http.put<any>(url, contacts);
    }

    removeContact(contactGroupId: number, contactId: number):Observable<any[]>{
      const url = `${this.apiUrl}/removeContact/${contactGroupId}/${contactId}`;
      return this.http.put<any>(url,null);
    }


    deleteGroupContact(contactGroupId: number):Observable<string> {
      const url = `${this.apiUrl}/delete/${contactGroupId}`;
      return this.http.delete<any>(url);
    }



}