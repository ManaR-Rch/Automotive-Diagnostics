import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { SidebarComponent } from '../../../components/sidebar/sidebar.component';
import { Appointment } from '../../../core/models/appointment.model';
import { AdminActions } from '../../../store/admin/admin.actions';
import { selectAdminAppointments, selectAdminLoading, selectAdminError } from '../../../store/admin/admin.selectors';

@Component({
  selector: 'app-admin-appointments',
  standalone: true,
  imports: [CommonModule, SidebarComponent],
  templateUrl: './admin-appointments.component.html'
})
export class AdminAppointmentsComponent implements OnInit {
  appointments$: Observable<Appointment[]>;
  loading$: Observable<boolean>;
  error$: Observable<string | null>;
  statuses = ['EN_ATTENTE', 'CONFIRME', 'EN_COURS', 'TERMINE', 'ANNULE'];

  constructor(private store: Store) {
    this.appointments$ = this.store.select(selectAdminAppointments);
    this.loading$ = this.store.select(selectAdminLoading);
    this.error$ = this.store.select(selectAdminError);
  }

  ngOnInit(): void {
    this.store.dispatch(AdminActions.loadAllAppointments());
  }

  updateStatus(id: number, statut: string): void {
    this.store.dispatch(AdminActions.updateAppointmentStatus({ id, statut }));
  }
}
