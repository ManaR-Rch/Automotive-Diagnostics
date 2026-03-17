import { inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, switchMap } from 'rxjs/operators';
import { of } from 'rxjs';
import { ServicesActions } from './services.actions';
import { ServiceApiService } from '../../services/service-api.service';

export const loadServicesEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(ServiceApiService)) =>
    actions$.pipe(
      ofType(ServicesActions.loadServices),
      switchMap(() => svc.getActiveServices().pipe(
        map(services => ServicesActions.loadServicesSuccess({ services })),
        catchError(err => of(ServicesActions.loadServicesFailure({ error: err.message })))
      ))
    ),
  { functional: true }
);

export const loadAllServicesEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(ServiceApiService)) =>
    actions$.pipe(
      ofType(ServicesActions.loadAllServices),
      switchMap(() => svc.getAllServices().pipe(
        map(services => ServicesActions.loadAllServicesSuccess({ services })),
        catchError(err => of(ServicesActions.loadAllServicesFailure({ error: err.message })))
      ))
    ),
  { functional: true }
);

export const createServiceEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(ServiceApiService)) =>
    actions$.pipe(
      ofType(ServicesActions.createService),
      switchMap(({ service }) => svc.createService(service).pipe(
        map(s => ServicesActions.createServiceSuccess({ service: s })),
        catchError(err => of(ServicesActions.createServiceFailure({ error: err.message })))
      ))
    ),
  { functional: true }
);

export const updateServiceEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(ServiceApiService)) =>
    actions$.pipe(
      ofType(ServicesActions.updateService),
      switchMap(({ id, service }) => svc.updateService(id, service).pipe(
        map(s => ServicesActions.updateServiceSuccess({ service: s })),
        catchError(err => of(ServicesActions.updateServiceFailure({ error: err.message })))
      ))
    ),
  { functional: true }
);

export const deleteServiceEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(ServiceApiService)) =>
    actions$.pipe(
      ofType(ServicesActions.deleteService),
      switchMap(({ id }) => svc.deleteService(id).pipe(
        map(() => ServicesActions.deleteServiceSuccess({ id })),
        catchError(err => of(ServicesActions.deleteServiceFailure({ error: err.message })))
      ))
    ),
  { functional: true }
);
