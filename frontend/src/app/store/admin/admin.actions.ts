import { createActionGroup, emptyProps, props } from '@ngrx/store';
import { User } from '../../core/models/user.model';
import { Appointment, DashboardStats } from '../../core/models/appointment.model';
import { Service } from '../../core/models/service.model';

export const AdminActions = createActionGroup({
  source: 'Admin',
  events: {
    'Load Stats':   emptyProps(),
    'Load Stats Success': props<{ stats: DashboardStats }>(),
    'Load Stats Failure': props<{ error: string }>(),

    'Load Users': emptyProps(),
    'Load Users Success': props<{ users: User[] }>(),
    'Load Users Failure': props<{ error: string }>(),
    'Create User': props<{ user: User & { motDePasse: string } }>(),
    'Create User Success': props<{ user: User }>(),
    'Create User Failure': props<{ error: string }>(),
    'Update User': props<{ id: number; user: User }>(),
    'Update User Success': props<{ user: User }>(),
    'Update User Failure': props<{ error: string }>(),
    'Toggle User': props<{ id: number }>(),
    'Toggle User Success': props<{ user: User }>(),
    'Toggle User Failure': props<{ error: string }>(),
    'Delete User': props<{ id: number }>(),
    'Delete User Success': props<{ id: number }>(),
    'Delete User Failure': props<{ error: string }>(),

    'Load All Appointments': emptyProps(),
    'Load All Appointments Success': props<{ appointments: Appointment[] }>(),
    'Load All Appointments Failure': props<{ error: string }>(),
    'Update Appointment Status': props<{ id: number; statut: string; notes?: string }>(),
    'Update Appointment Status Success': props<{ appointment: Appointment }>(),
    'Update Appointment Status Failure': props<{ error: string }>(),

    'Load Admin Services': emptyProps(),
    'Load Admin Services Success': props<{ services: Service[] }>(),
    'Load Admin Services Failure': props<{ error: string }>(),
    'Create Admin Service': props<{ service: Service }>(),
    'Create Admin Service Success': props<{ service: Service }>(),
    'Create Admin Service Failure': props<{ error: string }>(),
    'Update Admin Service': props<{ id: number; service: Service }>(),
    'Update Admin Service Success': props<{ service: Service }>(),
    'Update Admin Service Failure': props<{ error: string }>(),
    'Delete Admin Service': props<{ id: number }>(),
    'Delete Admin Service Success': props<{ id: number }>(),
    'Delete Admin Service Failure': props<{ error: string }>(),

    'WebSocket New Appointment': props<{ appointment: Appointment }>(),
    'WebSocket Update Appointment': props<{ appointment: Appointment }>(),

    'Clear Error': emptyProps(),
  }
});
