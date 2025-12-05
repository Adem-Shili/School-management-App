import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { StudentService, Student, StudentPage } from '../../services/student.service'; // Import interfaces and service

@Component({
  selector: 'app-students',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule], // Added ReactiveFormsModule
  templateUrl: './students.component.html',
})
export class StudentsComponent implements OnInit {
  private studentService = inject(StudentService);
  private authService = inject(AuthService);
  private router = inject(Router);
  private fb = inject(FormBuilder);

  // Data State
  students: Student[] = [];
  currentPage = 0;
  pageSize = 10;
  totalPages = 0;
  totalElements = 0;
  searchQuery = '';
  levelFilter = '';
  
  // UI State for Modal
  isModalOpen = false;
  isEditMode = false;
  loading = false;
  
  // Form State
  studentForm: FormGroup;
  currentStudentId: number | null = null;

  // Alerts/Messages
  message: { type: 'success' | 'error' | 'info'; text: string } | null = null;


  constructor() {
    this.studentForm = this.fb.group({
      name: ['', Validators.required],
      level: ['commun', Validators.required]
    });
    
  }

  ngOnInit(): void {
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/login']);
      return;
    }
    this.loadStudents();
  }

  private showMessage(type: 'success' | 'error' | 'info', text: string) {
    this.message = { type, text };
    setTimeout(() => this.message = null, 5000); // Clear message after 5 seconds
  }

  // --- Data Loading and Filtering ---

  loadStudents(page: number = this.currentPage) {
    this.loading = true;
    this.currentPage = page;

    this.studentService.getAllStudents(this.searchQuery, this.levelFilter, this.currentPage, this.pageSize)
      .subscribe({
        next: (pageData: StudentPage) => {
          this.students = pageData.content;
          this.totalPages = pageData.totalPages;
          this.totalElements = pageData.totalElements;
          this.currentPage = pageData.number;
          this.loading = false;
        },
        error: (err) => {
          this.showMessage('error', 'Failed to load students. Authentication error or service is down.');
          this.loading = false;
          console.error('API Error:', err);
        }
      });
  }

  onSearch(event: Event) {
    this.searchQuery = (event.target as HTMLInputElement).value;
    this.loadStudents(0); // Reset to first page on new search
  }

  onFilter(event: Event) {
    this.levelFilter = (event.target as HTMLSelectElement).value;
    this.loadStudents(0); // Reset to first page on new filter
  }

  // --- Pagination ---

  goToPage(page: number) {
    if (page >= 0 && page < this.totalPages) {
      this.loadStudents(page);
    }
  }
  stopPropagation(event: Event): void {
    event.stopPropagation();
  }
  
  get pageNumbers(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i);
  }

  // --- CRUD/Modal Methods ---

  openCreateModal() {
    this.isEditMode = false;
    this.currentStudentId = null;
    this.studentForm.reset({ level: 'commun' }); // Reset form and set default level
    this.isModalOpen = true;
  }

  openEditModal(student: Student) {
    this.isEditMode = true;
    this.currentStudentId = student.id || null;
    this.studentForm.patchValue({
      name: student.name,
      level: student.level
    });
    
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
    this.studentForm.reset();
  }

  saveStudent() {
    if (this.studentForm.invalid) {
      this.showMessage('error', 'Please fill in all required fields.');
      return;
    }

    this.loading = true;
    const studentData = {
      name: this.studentForm.value.username,   
      level: this.studentForm.value.level
    };
    

    const operation = this.isEditMode && this.currentStudentId !== null
      ? this.studentService.updateStudent(this.currentStudentId, studentData)
      : this.studentService.createStudent(studentData);

    operation.subscribe({
      next: () => {
        this.showMessage('success', `Student ${this.isEditMode ? 'updated' : 'created'} successfully!`);
        this.closeModal();
        this.loadStudents(this.isEditMode ? this.currentPage : 0); // Reload current or first page
      },
      error: (err) => {
        this.showMessage('error', `Failed to ${this.isEditMode ? 'update' : 'create'} student.`);
        console.error('API Error:', err);
        this.loading = false;
      }
    });
  }

  deleteStudent(id: number) {
    if (!confirm(`Are you sure you want to delete student ID: ${id}?`)) {
      return;
    }
    
    this.loading = true;
    this.studentService.deleteStudent(id).subscribe({
      next: () => {
        this.showMessage('success', `Student ID ${id} deleted successfully.`);
        // Try to stay on the same page, unless the page is now empty
        this.loadStudents(this.students.length === 1 && this.currentPage > 0 ? this.currentPage - 1 : this.currentPage);
      },
      error: (err) => {
        this.showMessage('error', `Failed to delete student ID ${id}.`);
        console.error('API Error:', err);
        this.loading = false;
      }
    });
  }

  // --- Import/Export Features ---
  
  exportStudents() {
    this.loading = true;
    console.log("Initiating CSV/Excel export");
    this.studentService.exportStudents().subscribe({
      next: (blob: Blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'students_export.csv';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        window.URL.revokeObjectURL(url);
        this.showMessage('success', 'Student list exported successfully!');
        this.loading = false;
      },
      error: (err) => {
        this.showMessage('error', 'Failed to export student list.');
        console.error('Export Error:', err);
        this.loading = false;
      }
    });
  }

  onImportCSV(event: Event) {
    const file = (event.target as HTMLInputElement)?.files?.[0];
    if (!file) return;

    this.loading = true;
    this.studentService.importStudents(file).subscribe({
      next: () => {
        this.showMessage('success', `${file.name} imported successfully!`);
        this.loadStudents(0); // Reload data after import
      },
      error: (err) => {
        this.showMessage('error', `Failed to import ${file.name}. Ensure format is correct.`);
        console.error('Import Error:', err);
        this.loading = false;
      }
    });
  }

  // --- Logout ---

  onLogout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}