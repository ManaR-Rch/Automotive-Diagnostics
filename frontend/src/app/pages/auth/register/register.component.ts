import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable, Subject, takeUntil } from 'rxjs';
import { AuthActions } from '../../../store/auth/auth.actions';
import { selectAuthLoading, selectAuthError, selectRegistered } from '../../../store/auth/auth.selectors';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register.component.html',
})
export class RegisterComponent implements OnInit, OnDestroy {
  form: FormGroup;
  loading$: Observable<boolean>;
  error$: Observable<string | null>;
  registered$: Observable<boolean>;
  showPassword = false;
  private destroy$ = new Subject<void>();

  constructor(private fb: FormBuilder, private store: Store, private router: Router) {
    this.form = this.fb.group({
      nom:        ['', [Validators.required, Validators.minLength(2)]],
      prenom:     ['', [Validators.required, Validators.minLength(2)]],
      email:      ['', [Validators.required, Validators.email]],
      telephone:  ['', [Validators.required, Validators.pattern(/^[0-9+\s\-]{8,15}$/)]],
      motDePasse: ['', [Validators.required, Validators.minLength(6)]],
    });
    this.loading$    = this.store.select(selectAuthLoading);
    this.error$      = this.store.select(selectAuthError);
    this.registered$ = this.store.select(selectRegistered);
  }

  ngOnInit() {
    this.registered$.pipe(takeUntil(this.destroy$)).subscribe(done => {
      if (done) setTimeout(() => this.router.navigate(['/auth/login']), 2000);
    });
  }

  ngOnDestroy() { this.destroy$.next(); this.destroy$.complete(); }

  submit() {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.store.dispatch(AuthActions.register({ request: this.form.value }));
  }

  clearError() { this.store.dispatch(AuthActions.clearError()); }
}
