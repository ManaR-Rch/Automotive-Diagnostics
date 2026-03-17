import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { SidebarComponent } from '../../components/sidebar/sidebar.component';
import { User } from '../../core/models/user.model';
import { AuthActions } from '../../store/auth/auth.actions';
import { selectCurrentUser, selectAuthLoading, selectAuthError } from '../../store/auth/auth.selectors';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, SidebarComponent],
  templateUrl: './profile.component.html'
})
export class ProfileComponent implements OnInit {
  user$: Observable<User | null>;
  loading$: Observable<boolean>;
  error$: Observable<string | null>;
  editMode = false;
  saved = false;
  showPasswordForm = false;

  form: FormGroup;
  passwordForm: FormGroup;
  showPassword = false;
  showNewPassword = false;

  constructor(private store: Store, private fb: FormBuilder, private authService: AuthService) {
    this.user$ = this.store.select(selectCurrentUser);
    this.loading$ = this.store.select(selectAuthLoading);
    this.error$ = this.store.select(selectAuthError);

    this.form = this.fb.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telephone: ['']
    });

    this.passwordForm = this.fb.group({
      motDePasseActuel: ['', Validators.required],
      nouveauMotDePasse: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  ngOnInit(): void {
    this.store.dispatch(AuthActions.loadProfile());
    this.user$.subscribe(user => {
      if (user) {
        this.form.patchValue({
          nom: user.nom,
          prenom: user.prenom,
          email: user.email,
          telephone: user.telephone
        });
      }
    });
  }

  saveProfile(): void {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.authService.updateProfile(this.form.value).subscribe({
      next: () => { this.saved = true; this.editMode = false; setTimeout(() => this.saved = false, 3000); },
      error: () => {}
    });
  }

  changePassword(): void {
    if (this.passwordForm.invalid) { this.passwordForm.markAllAsTouched(); return; }
    this.authService.changePassword(this.passwordForm.value).subscribe({
      next: () => { this.showPasswordForm = false; this.passwordForm.reset(); },
      error: () => {}
    });
  }

  field(f: FormGroup, name: string) { return f.get(name); }
}
