import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap, map } from 'rxjs';

// Define the shape of the login response
interface LoginResponse {
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:7070/api/auth';
  private http = inject(HttpClient);
  private TOKEN_KEY = 'auth_token';

  login(credentials: any): Observable<string> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        // Store the token upon successful login
        localStorage.setItem(this.TOKEN_KEY, response.token);
      }),
      map(response => response.token) // Return just the token to the component
    );
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}