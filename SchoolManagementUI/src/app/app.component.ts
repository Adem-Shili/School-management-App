// src/app/app.component.ts

import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router'; // Import RouterOutlet
import { CommonModule } from '@angular/common'; // Import CommonModule

@Component({
  selector: 'app-root',
  standalone: true,
  // Use RouterOutlet to display components based on the route
  imports: [CommonModule, RouterOutlet], 
  // Template only needs the router outlet
  template: `
    <router-outlet></router-outlet>
  `,
  styleUrls: ['./app.component.css'] // Or remove if empty
})
export class AppComponent {
  // Title is optional, but no more test logic is needed here
  title = 'school-management-frontend';
}