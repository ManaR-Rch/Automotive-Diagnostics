import { createFeatureSelector, createSelector } from '@ngrx/store';
import { ServicesState } from './services.reducer';

export const selectServicesState   = createFeatureSelector<ServicesState>('services');
export const selectAllServices     = createSelector(selectServicesState, s => s.services);
export const selectServicesLoading = createSelector(selectServicesState, s => s.loading);
export const selectServicesError   = createSelector(selectServicesState, s => s.error);
export const selectServiceCategories = createSelector(
  selectServicesState,
  s => [...new Set(s.services.map(srv => srv.categorie))]
);
