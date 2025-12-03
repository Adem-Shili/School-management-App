// src/app/app.routes.ts

import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { StudentsComponent } from './components/students/students.component';

export const routes: Routes = [
  // Authentication route
  { path: 'login', component: LoginComponent },

  // Protected route for student management (Requires AuthGuard implementation later)
  { path: 'students', component: StudentsComponent, canActivate: [/* AuthGuard will go here */] },

  // --- START HERE ---
  // Default path: Redirects to login when the app first loads (e.g., http://localhost:4200/)
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  
  // Handle 404/undefined routes: Redirects to login
  { path: '**', redirectTo: 'login' } 
];