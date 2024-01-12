import { Component, OnInit } from '@angular/core';
import { ContactGroupeService } from '../service/contactGroupe.service';
import { ContactService } from '../service/contact.service';
import { ActivatedRoute } from '@angular/router';
import {SelectionModel} from '@angular/cdk/collections';
import {MatTableDataSource} from '@angular/material/table';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-contact-group',
  templateUrl: './contact-group.component.html',
  styleUrls: ['./contact-group.component.scss']
})
export class ContactGroupComponent implements OnInit {

  group : any;
  groupId: any = null;
  contacts : any[] = [];
  contactList : any[] = []
  displayedColumns: string[] = ['select','Prénom','Nom','Email'];
  dataSource = new MatTableDataSource<any>;
  selection = new SelectionModel<any>(true, []);
  groupName !: string;
  groupeForm: FormGroup;

  constructor(private contactGroupService : ContactGroupeService,
    private contactService: ContactService,
    private route: ActivatedRoute,private fb: FormBuilder){
      this.groupeForm = this.fb.group({
        groupName: [this.groupName, Validators.required]
      });
    }


    ngOnInit(): void {
      this.route.paramMap.subscribe((params:any) => {
        this.groupId = JSON.parse(decodeURIComponent(params.get('data')));
      });
      if(this.groupId != null){
         this.getAllContacts();
         
      }
      
    }

    isAllSelected() {
      const numSelected = this.selection.selected.length;
      const numRows = this.dataSource.data.length;
      return numSelected === numRows;
    }
  
    toggleAllRows() {
      if (this.isAllSelected()) {
        this.selection.clear();
        return;
      }
  
      this.selection.select(...this.dataSource.data);
    }
  
    checkboxLabel(row?: any): string {
      if (!row) {
        return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
      }
      return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.position + 1}`;
    }

    getAllContacts(): void {
      const result1 = this.contactGroupService.getAContactGroup(this.groupId).subscribe(
        (data: any) => {
          this.group = data;
          this.contacts = data.contacts;
          this.groupName = data.nameGroup;
          this.groupeForm.patchValue({ groupName: this.groupName });
        }
      );

      const result2 = this.contactService.getAllContacts().subscribe(
        (data: any) => {
          this.contactList = data;
          this.filterContactList();
            this.dataSource = new MatTableDataSource(this.contactList);
          
        }
      );
    }

    filterContactList(): void {
      this.contactList = this.contactList.filter(contact => !this.contacts.some(c => c.idContact === contact.idContact));
    }

    removeContact(contactId : number){
      const contactGroupId = this.groupId; 

      this.contactGroupService.removeContact(contactGroupId, contactId).subscribe(
        (response) => {
          console.log('Contact supprimé avec succès !', response);
          this.getAllContacts();
        },
        (error) => {
          console.error('Erreur lors de la suppression du contact :', error);
          this.getAllContacts();
        }
      );
    }

    addContacts(){
      const contactGroupId = this.groupId; 
      const selectedContacts = this.selection.selected.map(contact => contact.idContact);
      this.contactGroupService.addContacts(contactGroupId, selectedContacts).subscribe(
        (response) => {
          console.log('Contacts ajoutés avec succès !', response);
    
          this.selection.clear();
          this.getAllContacts();
        },
        (error) => {
          console.error('Erreur lors de l\'ajout des contacts :', error);
          this.selection.clear();
          this.getAllContacts();
        }
      );
    }

    updateGroupName() {
      if (this.groupeForm.valid) {
        this.groupName = this.groupeForm.value.groupName;
        const updatedGroup = {
          groupName: this.groupName
        };

        this.contactGroupService.updateContactGroup(this.groupId,updatedGroup).subscribe(
          (response) => {
            console.log('Groupe mis à jour avec succès !', response);
            this.getAllContacts();
          },
          (error) => {
            console.error('Erreur lors de la mise à jour du groupe :', error);
            this.getAllContacts();
          }
        );
      }
    }

}
