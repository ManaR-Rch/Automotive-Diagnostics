import { createReducer, on } from '@ngrx/store';
import { AdminActions } from './admin.actions';
import { User } from '../../core/models/user.model';
import { Appointment, DashboardStats } from '../../core/models/appointment.model';
import { Service } from '../../core/models/service.model';

export interface AdminState {
  stats: DashboardStats | null;
  users: User[];
  appointments: Appointment[];
  services: Service[];
  loading: boolean;
  error: string | null;
}

const initialState: AdminState = {
  stats: null, users: [], appointments: [], services: [],
  loading: false, error: null
};

export const adminReducer = createReducer(
  initialState,
  on(AdminActions.loadStats, AdminActions.loadUsers, AdminActions.loadAllAppointments, AdminActions.loadAdminServices,
    AdminActions.createUser, AdminActions.updateUser, AdminActions.toggleUser, AdminActions.deleteUser,
    AdminActions.updateAppointmentStatus, AdminActions.createAdminService, AdminActions.updateAdminService, AdminActions.deleteAdminService,
    s => ({ ...s, loading: true, error: null })),

  on(AdminActions.loadStatsSuccess, (s, { stats }) => ({ ...s, loading: false, stats })),
  on(AdminActions.loadUsersSuccess, (s, { users }) => ({ ...s, loading: false, users })),
  on(AdminActions.createUserSuccess, (s, { user }) => ({ ...s, loading: false, users: [...s.users, user] })),
  on(AdminActions.updateUserSuccess, AdminActions.toggleUserSuccess, (s, { user }) => ({
    ...s, loading: false, users: s.users.map(u => u.id === user.id ? user : u)
  })),
  on(AdminActions.deleteUserSuccess, (s, { id }) => ({ ...s, loading: false, users: s.users.filter(u => u.id !== id) })),

  on(AdminActions.loadAllAppointmentsSuccess, (s, { appointments }) => ({ ...s, loading: false, appointments })),
  on(AdminActions.updateAppointmentStatusSuccess, (s, { appointment }) => ({
    ...s, loading: false,
    appointments: s.appointments.map(a => a.id === appointment.id ? appointment : a)
  })),

  on(AdminActions.loadAdminServicesSuccess, (s, { services }) => ({ ...s, loading: false, services })),
  on(AdminActions.createAdminServiceSuccess, (s, { service }) => ({ ...s, loading: false, services: [...s.services, service] })),
  on(AdminActions.updateAdminServiceSuccess, (s, { service }) => ({
    ...s, loading: false, services: s.services.map(sv => sv.id === service.id ? service : sv)
  })),
  on(AdminActions.deleteAdminServiceSuccess, (s, { id }) => ({
    ...s, loading: false, services: s.services.filter(sv => sv.id !== id)
  })),

  on(AdminActions.loadStatsFailure, AdminActions.loadUsersFailure, AdminActions.loadAllAppointmentsFailure,
    AdminActions.loadAdminServicesFailure, AdminActions.createUserFailure, AdminActions.updateUserFailure,
    AdminActions.toggleUserFailure, AdminActions.deleteUserFailure, AdminActions.updateAppointmentStatusFailure,
    AdminActions.createAdminServiceFailure, AdminActions.updateAdminServiceFailure, AdminActions.deleteAdminServiceFailure,
    (s, { error }) => ({ ...s, loading: false, error })),
  on(AdminActions.clearError, s => ({ ...s, error: null })),
);
