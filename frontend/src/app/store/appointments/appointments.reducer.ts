import { createReducer, on } from '@ngrx/store';
import { AppointmentsActions } from './appointments.actions';
import { Appointment } from '../../core/models/appointment.model';

export interface AppointmentsState {
  appointments: Appointment[];
  loading: boolean;
  error: string | null;
  success: string | null;
}

const initialState: AppointmentsState = { appointments: [], loading: false, error: null, success: null };

export const appointmentsReducer = createReducer(
  initialState,
  on(AppointmentsActions.loadMyAppointments, AppointmentsActions.createAppointment, AppointmentsActions.cancelAppointment,
    s => ({ ...s, loading: true, error: null, success: null })),
  on(AppointmentsActions.loadMyAppointmentsSuccess, (s, { appointments }) => ({ ...s, loading: false, appointments })),
  on(AppointmentsActions.createAppointmentSuccess, (s, { appointment }) => ({
    ...s, loading: false, appointments: [...s.appointments, appointment], success: 'Rendez-vous créé avec succès!'
  })),
  on(AppointmentsActions.cancelAppointmentSuccess, (s, { appointment }) => ({
    ...s, loading: false,
    appointments: s.appointments.map(a => a.id === appointment.id ? appointment : a),
    success: 'Rendez-vous annulé avec succès!'
  })),
  on(AppointmentsActions.loadMyAppointmentsFailure, AppointmentsActions.createAppointmentFailure, AppointmentsActions.cancelAppointmentFailure,
    (s, { error }) => ({ ...s, loading: false, error })),
  on(AppointmentsActions.clearError, s => ({ ...s, error: null, success: null })),
);
