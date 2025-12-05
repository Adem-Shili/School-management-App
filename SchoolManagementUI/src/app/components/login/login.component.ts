import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  loginRequest = { name: '', password: '' };
  errorMessage: string | null = null;

  onSubmit() {
    this.errorMessage = null;
    this.authService.login(this.loginRequest).subscribe({
      next: (token) => {
        console.log('Login successful. Token:', token);
        this.router.navigate(['/students']);
      },
      error: (err) => {
        this.errorMessage = 'Invalid username or password. Please try again.';
        console.error('Login error:', err);
      },
    });
  }
}