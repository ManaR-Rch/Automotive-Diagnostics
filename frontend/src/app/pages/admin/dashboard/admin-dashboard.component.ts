import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { SidebarComponent } from '../../../components/sidebar/sidebar.component';
import { DashboardStats, Appointment } from '../../../core/models/appointment.model';
import { User } from '../../../core/models/user.model';
import { AdminActions } from '../../../store/admin/admin.actions';
import { selectAdminStats, selectAdminLoading, selectAdminUsers, selectAdminAppointments } from '../../../store/admin/admin.selectors';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, SidebarComponent],
  templateUrl: './admin-dashboard.component.html'
})
export class AdminDashboardComponent implements OnInit {
  stats$: Observable<DashboardStats | null>;
  loading$: Observable<boolean>;
  users$: Observable<User[]>;
  appointments$: Observable<Appointment[]>;
  // Helpers pour afficher nom/téléphone
  private usersCache: User[] = [];

  constructor(private store: Store) {
    this.stats$ = this.store.select(selectAdminStats);
    this.loading$ = this.store.select(selectAdminLoading);
    this.users$ = this.store.select(selectAdminUsers);
    this.appointments$ = this.store.select(selectAdminAppointments);
  }

  ngOnInit(): void {
    this.store.dispatch(AdminActions.loadStats());
    this.store.dispatch(AdminActions.loadUsers());
    this.store.dispatch(AdminActions.loadAllAppointments());
    this.users$.subscribe(users => this.usersCache = users);
  }

  getUserName(userId?: number): string {
    const u = this.usersCache.find(u => u.id === userId);
    return u ? `${u.nom} ${u.prenom}` : '-';
  }
  getUserPhone(userId?: number): string {
    const u = this.usersCache.find(u => u.id === userId);
    return u?.telephone || '-';
  }
}
