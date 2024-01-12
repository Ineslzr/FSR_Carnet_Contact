// login.component.ts
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  login: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private router: Router) {}

  onSubmit(): void {

    if (this.login == this.password) {
      this.router.navigate(['/contact']);
    } else {
      this.errorMessage = 'Identifiants incorrects';
    }
  }
}
