import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { SidebarComponent } from '../../components/sidebar/sidebar.component';
import { Appointment } from '../../core/models/appointment.model';
import { Vehicle } from '../../core/models/vehicle.model';
import { Service } from '../../core/models/service.model';
import { AppointmentsActions } from '../../store/appointments/appointments.actions';
import { VehiclesActions } from '../../store/vehicles/vehicles.actions';
import { ServicesActions } from '../../store/services/services.actions';
import { selectAllAppointments, selectAppointmentsLoading, selectAppointmentsError } from '../../store/appointments/appointments.selectors';
import { selectAllVehicles } from '../../store/vehicles/vehicles.selectors';
import { selectAllServices } from '../../store/services/services.selectors';

@Component({
  selector: 'app-appointments',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, SidebarComponent],
  templateUrl: './appointments.component.html',
  styleUrl: './appointments.component.css'
})
export class AppointmentsComponent implements OnInit {
  appointments$: Observable<Appointment[]>;
  vehicles$: Observable<Vehicle[]>;
  services$: Observable<Service[]>;
  loading$: Observable<boolean>;
  error$: Observable<string | null>;

  showForm = false;
  cancelConfirmId: number | null = null;

  form: FormGroup;
  today = new Date().toISOString().split('T')[0];

  constructor(private store: Store, private fb: FormBuilder) {
    this.appointments$ = this.store.select(selectAllAppointments);
    this.vehicles$ = this.store.select(selectAllVehicles);
    this.services$ = this.store.select(selectAllServices);
    this.loading$ = this.store.select(selectAppointmentsLoading);
    this.error$ = this.store.select(selectAppointmentsError);

    this.form = this.fb.group({
      vehiculeId: ['', Validators.required],
      serviceId: ['', Validators.required],
      dateRdv: ['', Validators.required],
      descriptionProbleme: [''],
      urgence: ['NORMALE']
    });
  }

  ngOnInit(): void {
    this.store.dispatch(AppointmentsActions.loadMyAppointments());
    this.store.dispatch(VehiclesActions.loadMyVehicles());
    this.store.dispatch(ServicesActions.loadServices());
  }

  submitBooking(): void {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.store.dispatch(AppointmentsActions.createAppointment({ appointment: this.form.value }));
    this.showForm = false;
    this.form.reset({ urgence: 'NORMALE' });
  }

  confirmCancel(id: number): void {
    this.cancelConfirmId = id;
  }

  cancelAppointment(): void {
    if (this.cancelConfirmId) {
      this.store.dispatch(AppointmentsActions.cancelAppointment({ id: this.cancelConfirmId }));
      this.cancelConfirmId = null;
    }
  }

  getStatutClass(statut: string): string {
    const map: Record<string, string> = {
      'EN_ATTENTE': 'badge-EN_ATTENTE',
      'CONFIRME': 'badge-CONFIRME',
      'EN_COURS': 'badge-EN_COURS',
      'TERMINE': 'badge-TERMINE',
      'ANNULE': 'badge-ANNULE'
    };
    return map[statut] || 'badge-EN_ATTENTE';
  }

  canCancel(statut: string): boolean {
    return ['EN_ATTENTE', 'CONFIRME'].includes(statut);
  }
}
