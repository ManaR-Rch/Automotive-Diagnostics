import { createFeatureSelector, createSelector } from '@ngrx/store';
import { AdminState } from './admin.reducer';

export const selectAdminState = createFeatureSelector<AdminState>('admin');
export const selectAdminStats        = createSelector(selectAdminState, s => s.stats);
export const selectAdminUsers        = createSelector(selectAdminState, s => s.users);
export const selectAdminAppointments = createSelector(selectAdminState, s => s.appointments);
export const selectAdminServices     = createSelector(selectAdminState, s => s.services);
export const selectAdminLoading      = createSelector(selectAdminState, s => s.loading);
export const selectAdminError        = createSelector(selectAdminState, s => s.error);
