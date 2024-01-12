import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'CarnetContact';

  constructor(private router: Router) {}

  isLoginPage(): boolean {
    return this.router.url === '/';
  }
}