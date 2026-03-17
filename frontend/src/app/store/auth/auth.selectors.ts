import { createFeatureSelector, createSelector } from '@ngrx/store';
import { AuthState } from './auth.reducer';

export const selectAuthState = createFeatureSelector<AuthState>('auth');
export const selectIsAuthenticated = createSelector(selectAuthState, s => !!s.token);
export const selectCurrentUser     = createSelector(selectAuthState, s => s.user);
export const selectAuthLoading     = createSelector(selectAuthState, s => s.loading);
export const selectAuthError       = createSelector(selectAuthState, s => s.error);
export const selectRegistered      = createSelector(selectAuthState, s => s.registered);
export const selectIsAdmin         = createSelector(selectAuthState, s => s.user?.role === 'ADMIN');
