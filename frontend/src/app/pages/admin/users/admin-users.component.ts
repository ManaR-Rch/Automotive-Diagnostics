import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { SidebarComponent } from '../../../components/sidebar/sidebar.component';
import { User } from '../../../core/models/user.model';
import { AdminActions } from '../../../store/admin/admin.actions';
import { selectAdminUsers, selectAdminLoading, selectAdminError } from '../../../store/admin/admin.selectors';

@Component({
  selector: 'app-admin-users',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, SidebarComponent],
  templateUrl: './admin-users.component.html'
})
export class AdminUsersComponent implements OnInit {
  users$: Observable<User[]>;
  loading$: Observable<boolean>;
  error$: Observable<string | null>;
  showCreate = false;

  form: FormGroup;

  constructor(private store: Store, private fb: FormBuilder) {
    this.users$ = this.store.select(selectAdminUsers);
    this.loading$ = this.store.select(selectAdminLoading);
    this.error$ = this.store.select(selectAdminError);
    this.form = this.fb.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telephone: [''],
      role: ['CLIENT', Validators.required],
      motDePasse: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  ngOnInit(): void {
    this.store.dispatch(AdminActions.loadUsers());
  }

  createUser(): void {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.store.dispatch(AdminActions.createUser({ user: this.form.value as any }));
    this.form.reset({ role: 'CLIENT' as any });
    this.showCreate = false;
  }

  toggleUser(id: number): void { this.store.dispatch(AdminActions.toggleUser({ id })); }
  deleteUser(id: number): void { this.store.dispatch(AdminActions.deleteUser({ id })); }
}
