import { inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, switchMap } from 'rxjs/operators';
import { of } from 'rxjs';
import { VehiclesActions } from './vehicles.actions';
import { VehicleService } from '../../services/vehicle.service';

export const loadVehiclesEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(VehicleService)) =>
    actions$.pipe(
      ofType(VehiclesActions.loadMyVehicles),
      switchMap(() =>
        svc.getMyVehicles().pipe(
          map(vehicles => VehiclesActions.loadMyVehiclesSuccess({ vehicles })),
          catchError(err => of(VehiclesActions.loadMyVehiclesFailure({ error: err.message })))
        )
      )
    ),
  { functional: true }
);

export const createVehicleEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(VehicleService)) =>
    actions$.pipe(
      ofType(VehiclesActions.createVehicle),
      switchMap(({ vehicle }) =>
        svc.createVehicle(vehicle).pipe(
          map(v => VehiclesActions.createVehicleSuccess({ vehicle: v })),
          catchError(err => of(VehiclesActions.createVehicleFailure({ error: err.error?.message || err.message })))
        )
      )
    ),
  { functional: true }
);

export const updateVehicleEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(VehicleService)) =>
    actions$.pipe(
      ofType(VehiclesActions.updateVehicle),
      switchMap(({ id, vehicle }) =>
        svc.updateVehicle(id, vehicle).pipe(
          map(v => VehiclesActions.updateVehicleSuccess({ vehicle: v })),
          catchError(err => of(VehiclesActions.updateVehicleFailure({ error: err.message })))
        )
      )
    ),
  { functional: true }
);

export const deleteVehicleEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(VehicleService)) =>
    actions$.pipe(
      ofType(VehiclesActions.deleteVehicle),
      switchMap(({ id }) =>
        svc.deleteVehicle(id).pipe(
          map(() => VehiclesActions.deleteVehicleSuccess({ id })),
          catchError(err => of(VehiclesActions.deleteVehicleFailure({ error: err.message })))
        )
      )
    ),
  { functional: true }
);
