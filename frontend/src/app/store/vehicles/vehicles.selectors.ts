import { createFeatureSelector, createSelector } from '@ngrx/store';
import { VehiclesState } from './vehicles.reducer';

export const selectVehiclesState = createFeatureSelector<VehiclesState>('vehicles');
export const selectAllVehicles   = createSelector(selectVehiclesState, s => s.vehicles);
export const selectVehiclesLoading = createSelector(selectVehiclesState, s => s.loading);
export const selectVehiclesError   = createSelector(selectVehiclesState, s => s.error);
export const selectSelectedVehicleId = createSelector(selectVehiclesState, s => s.selectedId);
export const selectSelectedVehicle   = createSelector(
  selectVehiclesState,
  s => s.vehicles.find(v => v.id === s.selectedId) ?? null
);
