import { Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ServicesComponent } from './pages/services/services.component';
import { AppointmentsComponent } from './pages/appointments/appointments.component';
import { VehiclesComponent } from './pages/vehicles/vehicles.component';

export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'services', component: ServicesComponent },
  { path: 'appointments', component: AppointmentsComponent },
  { path: 'vehicles', component: VehiclesComponent },
  { path: '**', redirectTo: '/dashboard' }
];
