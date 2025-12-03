// src/app/services/student.service.ts
import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private apiUrl = 'http://localhost:7070/api/students'; 
  private http = inject(HttpClient);
  private authService = inject(AuthService);

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  // --- CRUD Operations ---

  getAllStudents(search?: string, level?: string, page?: number, size?: number): Observable<any> {
    const headers = this.getAuthHeaders();
    let params = {};
    if (search) params = { ...params, search };
    if (level) params = { ...params, level };
    if (page) params = { ...params, page: page.toString() };
    if (size) params = { ...params, size: size.toString() };
    
    return this.http.get(this.apiUrl, { headers, params });
  }

  createStudent(studentData: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(this.apiUrl, studentData, { headers });
  }

  updateStudent(id: number, studentData: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put(`${this.apiUrl}/${id}`, studentData, { headers });
  }

  deleteStudent(id: number): Observable<void> {
    const headers = this.getAuthHeaders();
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers });
  }
  
  // --- Extra Features ---

  exportStudents(): Observable<Blob> {
      const headers = this.getAuthHeaders();
      // Ensure your backend returns the CSV/Excel file as a byte stream
      return this.http.get(`${this.apiUrl}/export`, { headers, responseType: 'blob' });
  }
  
  importStudents(file: File): Observable<any> {
      const headers = this.getAuthHeaders();
      const formData = new FormData();
      formData.append('file', file, file.name);

      // Note: HttpClient automatically sets Content-Type for FormData
      return this.http.post(`${this.apiUrl}/import`, formData, { headers });
  }
}