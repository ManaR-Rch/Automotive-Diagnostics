import { createActionGroup, emptyProps, props } from '@ngrx/store';
import { Vehicle } from '../../core/models/vehicle.model';

export const VehiclesActions = createActionGroup({
  source: 'Vehicles',
  events: {
    'Load My Vehicles':    emptyProps(),
    'Load My Vehicles Success': props<{ vehicles: Vehicle[] }>(),
    'Load My Vehicles Failure': props<{ error: string }>(),
    'Create Vehicle':      props<{ vehicle: Vehicle }>(),
    'Create Vehicle Success': props<{ vehicle: Vehicle }>(),
    'Create Vehicle Failure': props<{ error: string }>(),
    'Update Vehicle':      props<{ id: number; vehicle: Vehicle }>(),
    'Update Vehicle Success': props<{ vehicle: Vehicle }>(),
    'Update Vehicle Failure': props<{ error: string }>(),
    'Delete Vehicle':      props<{ id: number }>(),
    'Delete Vehicle Success': props<{ id: number }>(),
    'Delete Vehicle Failure': props<{ error: string }>(),
    'Select Vehicle':      props<{ id: number | null }>(),
    'Clear Error':         emptyProps(),
  }
});
