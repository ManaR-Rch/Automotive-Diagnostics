import { createReducer, on } from '@ngrx/store';
import { VehiclesActions } from './vehicles.actions';
import { Vehicle } from '../../core/models/vehicle.model';

export interface VehiclesState {
  vehicles: Vehicle[];
  selectedId: number | null;
  loading: boolean;
  error: string | null;
}

const initialState: VehiclesState = { vehicles: [], selectedId: null, loading: false, error: null };

export const vehiclesReducer = createReducer(
  initialState,
  on(VehiclesActions.loadMyVehicles, VehiclesActions.createVehicle, VehiclesActions.updateVehicle, VehiclesActions.deleteVehicle,
    s => ({ ...s, loading: true, error: null })),
  on(VehiclesActions.loadMyVehiclesSuccess, (s, { vehicles }) => ({ ...s, loading: false, vehicles })),
  on(VehiclesActions.createVehicleSuccess, (s, { vehicle }) => ({ ...s, loading: false, vehicles: [...s.vehicles, vehicle] })),
  on(VehiclesActions.updateVehicleSuccess, (s, { vehicle }) => ({
    ...s, loading: false,
    vehicles: s.vehicles.map(v => v.id === vehicle.id ? vehicle : v)
  })),
  on(VehiclesActions.deleteVehicleSuccess, (s, { id }) => ({
    ...s, loading: false,
    vehicles: s.vehicles.filter(v => v.id !== id)
  })),
  on(VehiclesActions.loadMyVehiclesFailure, VehiclesActions.createVehicleFailure,
    VehiclesActions.updateVehicleFailure, VehiclesActions.deleteVehicleFailure,
    (s, { error }) => ({ ...s, loading: false, error })),
  on(VehiclesActions.selectVehicle, (s, { id }) => ({ ...s, selectedId: id })),
  on(VehiclesActions.clearError, s => ({ ...s, error: null })),
);
