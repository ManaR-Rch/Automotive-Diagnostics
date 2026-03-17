import { createFeatureSelector, createSelector } from '@ngrx/store';
import { AppointmentsState } from './appointments.reducer';

export const selectAppointmentsState   = createFeatureSelector<AppointmentsState>('appointments');
export const selectAllAppointments     = createSelector(selectAppointmentsState, s => s.appointments);
export const selectAppointmentsLoading = createSelector(selectAppointmentsState, s => s.loading);
export const selectAppointmentsError   = createSelector(selectAppointmentsState, s => s.error);
export const selectAppointmentsSuccess = createSelector(selectAppointmentsState, s => s.success);
