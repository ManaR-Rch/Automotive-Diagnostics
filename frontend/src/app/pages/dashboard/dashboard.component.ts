import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable, Subject } from 'rxjs';
import { map, takeUntil } from 'rxjs/operators';
import { selectCurrentUser } from '../../store/auth/auth.selectors';
import { VehiclesActions } from '../../store/vehicles/vehicles.actions';
import { AppointmentsActions } from '../../store/appointments/appointments.actions';
import { selectAllVehicles } from '../../store/vehicles/vehicles.selectors';
import { selectAllAppointments } from '../../store/appointments/appointments.selectors';
import { User } from '../../core/models/user.model';
import { Vehicle } from '../../core/models/vehicle.model';
import { Appointment } from '../../core/models/appointment.model';
import { SidebarComponent } from '../../components/sidebar/sidebar.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, SidebarComponent],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit, OnDestroy {
  user$: Observable<User | null>;
  vehicles$: Observable<Vehicle[]>;
  appointments$: Observable<Appointment[]>;
  confirmedCount$: Observable<number>;
  pendingCount$: Observable<number>;
  recentAppointments$: Observable<Appointment[]>;
  recentVehicles$: Observable<Vehicle[]>;
  private destroy$ = new Subject<void>();

  constructor(private store: Store, private router: Router) {
    this.user$ = this.store.select(selectCurrentUser);
    this.vehicles$ = this.store.select(selectAllVehicles);
    this.appointments$ = this.store.select(selectAllAppointments);
    this.confirmedCount$ = this.appointments$.pipe(
      map(list => list.filter(a => a.statut === 'CONFIRME').length)
    );
    this.pendingCount$ = this.appointments$.pipe(
      map(list => list.filter(a => a.statut === 'EN_ATTENTE').length)
    );
    this.recentAppointments$ = this.appointments$.pipe(
      map(list => list.slice(0, 4))
    );
    this.recentVehicles$ = this.vehicles$.pipe(
      map(list => list.slice(0, 4))
    );
  }

  ngOnInit() {
    this.user$.pipe(takeUntil(this.destroy$)).subscribe(user => {
      if (user?.role === 'ADMIN') {
        this.router.navigate(['/admin/dashboard']);
      }
    });

    this.store.dispatch(VehiclesActions.loadMyVehicles());
    this.store.dispatch(AppointmentsActions.loadMyAppointments());
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  getStatutClass(statut: string): string {
    const map: Record<string,string> = {
      EN_ATTENTE: 'badge-EN_ATTENTE', CONFIRME: 'badge-CONFIRME',
      EN_COURS: 'badge-EN_COURS', TERMINE: 'badge-TERMINE', ANNULE: 'badge-ANNULE'
    };
    return map[statut] || 'badge';
  }
}
