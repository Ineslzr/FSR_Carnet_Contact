import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ContactGroupeService } from '../service/contactGroupe.service'; // Remplacez par le chemin correct
import { Router } from '@angular/router';
@Component({
  selector: 'app-contact-groupe-list',
  templateUrl: './contact-groupe-list.component.html',
  styleUrls: ['./contact-groupe-list.component.scss']
})
export class ContactGroupeListComponent implements OnInit {

  createGroupForm!: FormGroup;
  groupList: any[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private contactGroupService: ContactGroupeService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.getAllContactGroups();
  }

  initForm() {
    this.createGroupForm = this.formBuilder.group({
      groupName: ['', Validators.required]
    });
  }

  getAllContactGroups(): void {
    const result = this.contactGroupService.getAllContactGroups().subscribe(
      (data: any) => {
        this.groupList = data;
      }
    );
  }

  deleteGroup(groupId:number){
    this.contactGroupService.deleteGroupContact(groupId).subscribe(
      (response) => {
        console.log('Groupe supprimé avec succès !', response);
        this.getAllContactGroups();
      },
      (error) => {
        console.error('Erreur lors de la suppression du groupe :', error);
        this.getAllContactGroups();
      }
    );
  }

  onSubmit(): void {
    if (this.createGroupForm.valid) {
      const contactGroup = this.createGroupForm.value;

      this.contactGroupService.createContactGroup(contactGroup).subscribe(
        response => {
          this.createGroupForm.reset();
          this.getAllContactGroups();
        },
        error => {
          console.error('Erreur lors de la création du groupe:', error);
          this.getAllContactGroups();
        }
      );
    }
  }

  redirectToTarget(group: any) {
    const encodedData = encodeURIComponent(JSON.stringify(group.idContactGroup)); 
    this.router.navigate(['/contact-group', encodedData]);
  }
}
