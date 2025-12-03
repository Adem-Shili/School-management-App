// src/app/components/students/students.component.ts
import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service'; // For logout
import { StudentService } from '../../services/student.service'; // To be created

// Define the entity structure
interface Student {
  id: number;
  username: string;
  level: string; // Should match your enum ('commun', 'specialite', 'terminal')
}

@Component({
  selector: 'app-students',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './students.component.html',
})
export class StudentsComponent implements OnInit {
  private studentService = inject(StudentService);
  private authService = inject(AuthService);
  private router = inject(Router);

  students: Student[] = []; // List of students to display
  // State variables for CRUD/filters would go here

  ngOnInit(): void {
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/login']);
      return;
    }
    this.loadStudents();
  }

  loadStudents() {
    // This is a placeholder; replace with actual service call
    // this.studentService.getAllStudents().subscribe(data => this.students = data);
    
    // MOCK DATA: Remove this once you implement the service
    this.students = [
      { id: 1, username: 'Alice Smith', level: 'commun' },
      { id: 2, username: 'Bob Johnson', level: 'specialite' },
      { id: 3, username: 'Charlie Brown', level: 'terminal' },
    ];
  }

  // --- CRUD/Action Methods ---

  openCreateModal() {
    console.log("Open modal to create student");
  }

  openEditModal(student: Student) {
    console.log("Open modal to edit student:", student);
  }

  deleteStudent(id: number) {
    if (confirm('Are you sure you want to delete this student?')) {
      console.log("Calling service to delete student ID:", id);
      // this.studentService.deleteStudent(id).subscribe(() => this.loadStudents());
    }
  }

  onSearch(event: Event) {
    const term = (event.target as HTMLInputElement).value;
    console.log("Searching for:", term);
    // Call loadStudents with search parameter
  }

  onFilter(event: Event) {
    const level = (event.target as HTMLSelectElement).value;
    console.log("Filtering by level:", level);
    // Call loadStudents with filter parameter
  }

  exportStudents() {
    console.log("Initiating CSV/Excel export");
    // Call studentService.exportStudents()
  }

  onImportCSV(event: Event) {
    const file = (event.target as HTMLInputElement)?.files?.[0];
    if (file) {
      console.log("File selected for import:", file.name);
      // Call studentService.importStudents(file)
    }
  }
  
  onLogout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}