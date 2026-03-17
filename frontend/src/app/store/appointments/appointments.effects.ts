import { inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, switchMap } from 'rxjs/operators';
import { of } from 'rxjs';
import { AppointmentsActions } from './appointments.actions';
import { AppointmentService } from '../../services/appointment.service';

export const loadAppointmentsEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(AppointmentService)) =>
    actions$.pipe(
      ofType(AppointmentsActions.loadMyAppointments),
      switchMap(() => svc.getMyAppointments().pipe(
        map(appointments => AppointmentsActions.loadMyAppointmentsSuccess({ appointments })),
        catchError(err => of(AppointmentsActions.loadMyAppointmentsFailure({ error: err.message })))
      ))
    ),
  { functional: true }
);

export const createAppointmentEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(AppointmentService)) =>
    actions$.pipe(
      ofType(AppointmentsActions.createAppointment),
      switchMap(({ appointment }) => svc.createAppointment(appointment).pipe(
        map(a => AppointmentsActions.createAppointmentSuccess({ appointment: a })),
        catchError(err => of(AppointmentsActions.createAppointmentFailure({ error: err.error?.message || err.message })))
      ))
    ),
  { functional: true }
);

export const cancelAppointmentEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(AppointmentService)) =>
    actions$.pipe(
      ofType(AppointmentsActions.cancelAppointment),
      switchMap(({ id }) => svc.cancelAppointment(id).pipe(
        map(a => AppointmentsActions.cancelAppointmentSuccess({ appointment: a })),
        catchError(err => of(AppointmentsActions.cancelAppointmentFailure({ error: err.message })))
      ))
    ),
  { functional: true }
);
