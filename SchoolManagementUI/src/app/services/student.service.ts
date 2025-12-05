import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Define the core Student entity structure
export interface Student {
  id?: number;
  name: string;
  level: 'commun' | 'specialite' | 'terminal';
}


// Define the expected backend response structure for a paginated list
export interface StudentPage {
    content: Student[];
    totalPages: number;
    totalElements: number;
    number: number; // Current page (0-indexed)
    size: number;
    first: boolean;
    last: boolean;
}


@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private apiUrl = 'http://localhost:7070/api/students'; 
  private http = inject(HttpClient);

  // --- CRUD Operations ---

  getAllStudents(search?: string, level?: string, page: number = 0, size: number = 10): Observable<StudentPage> {
    let params: any = { page: page.toString(), size: size.toString() }; 

    if (search) params = { ...params, search };
    if (level) params = { ...params, level };
    
    // The interceptor automatically adds the Authorization header.
    return this.http.get<StudentPage>(this.apiUrl, { params }); 
  }

  createStudent(studentData: Student): Observable<Student> {
    return this.http.post<Student>(this.apiUrl, studentData);
  }

  updateStudent(id: number, studentData: Student): Observable<Student> {
    return this.http.put<Student>(`${this.apiUrl}/${id}`, studentData);
  }

  deleteStudent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
  
  // --- Extra Features ---

  exportStudents(): Observable<Blob> {
      // The interceptor automatically adds headers here.
      return this.http.get(`${this.apiUrl}/export`, { responseType: 'blob' });
  }
  
  importStudents(file: File): Observable<any> {
      const formData = new FormData();
      formData.append('file', file, file.name);

      // HttpClient sets Content-Type for FormData, interceptor adds Authorization.
      return this.http.post(`${this.apiUrl}/import`, formData);
  }
}