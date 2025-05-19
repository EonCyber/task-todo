import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { UiModule } from '../../../shared/ui.module';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { matchPasswords } from '../../../shared/util/validators/validators';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-signup',
  imports: [CommonModule, UiModule, ReactiveFormsModule, RouterModule],
  providers: [AuthService],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  fb = inject(FormBuilder);
  loading = false;
  public signupForm: FormGroup;
  constructor(private authService: AuthService) {
    this.signupForm = this.fb.group({
      fullName: ['', Validators.required],
      email: [null, [Validators.required, Validators.email]],
      password: [null, Validators.required],
      confirmPassword: [null, Validators.required],
      avatarUrl: [null],
    },
    {
      validators: matchPasswords
    });
  }
  onSubmit() {
    if (this.signupForm.valid) {
      console.table(this.signupForm.value)
      this.loading = true;
      this.authService.handleSignUp(this.signupForm.value);
    } else {
      this.signupForm.markAllAsTouched();
    }
  }
}
