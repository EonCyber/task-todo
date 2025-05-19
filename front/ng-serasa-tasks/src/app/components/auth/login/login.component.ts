import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { UiModule } from '../../../shared/ui.module';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'login-page',
  imports: [CommonModule, UiModule, ReactiveFormsModule, RouterModule],
  providers: [AuthService],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  fb = inject(FormBuilder);
  loading = false;
  public form: FormGroup;
  constructor(private authService: AuthService) {
    this.form = this.fb.group({
      email: ['',[Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }


  onSubmit() {
    if (this.form.valid) {
      this.loading = true;
      console.table(this.form.value);
      this.authService.handleLogin(this.form.value);
    } else {
      this.form.markAllAsTouched();
      this.form.markAsDirty();
    }
  }
}
