import { inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, filter, map, switchMap, withLatestFrom } from 'rxjs/operators';
import { of } from 'rxjs';
import { Store } from '@ngrx/store';
import { selectCurrentUser } from '../auth/auth.selectors';
import { WebSocketService } from '../../core/services/websocket.service';
import { AppointmentsActions } from './appointments.actions';
import { AppointmentService } from '../../services/appointment.service';

export const loadAppointmentsEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(AppointmentService)) =>
    actions$.pipe(
      ofType(AppointmentsActions.loadMyAppointments),
      switchMap(() => svc.getMyAppointments().pipe(
        map(appointments => AppointmentsActions.loadMyAppointmentsSuccess({ appointments })),
        catchError(err => of(AppointmentsActions.loadMyAppointmentsFailure({
          error: err.error?.message || (err.status === 403
            ? 'Acces refuse. Veuillez vous reconnecter.'
            : 'Impossible de charger vos rendez-vous.')
        })))
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
        catchError(err => of(AppointmentsActions.cancelAppointmentFailure({
          error: err.error?.message || (err.status === 403
            ? 'Operation non autorisee. Veuillez vous reconnecter.'
            : 'Impossible d\'annuler ce rendez-vous.')
        })))
      ))
    ),
  { functional: true }
);

export const webSocketClientNewAppointmentEffect = createEffect(
  (ws = inject(WebSocketService), store = inject(Store)) =>
    ws.newRdv$.pipe(
      withLatestFrom(store.select(selectCurrentUser)),
      filter(([notification, user]) => {
        return !!user && user.role !== 'ADMIN' && notification.userEmail === user.email;
      }),
      map(([notification]) => AppointmentsActions.webSocketClientNewAppointment({
        appointment: {
          id: notification.id,
          statut: notification.statut,
          dateRdv: notification.dateRdv,
          serviceNom: notification.service,
          vehiculeNom: notification.vehicule
        } as any
      })),
      catchError(err => { console.error('WebSocket error:', err); return of(); })
    ),
  { functional: true }
);

export const webSocketClientUpdateAppointmentEffect = createEffect(
  (ws = inject(WebSocketService), store = inject(Store)) =>
    ws.updateRdv$.pipe(
      withLatestFrom(store.select(selectCurrentUser)),
      filter(([notification, user]) => {
        return !!user && user.role !== 'ADMIN' && notification.userEmail === user.email;
      }),
      map(([notification]) => AppointmentsActions.webSocketClientUpdateAppointment({
        appointment: {
          id: notification.id,
          statut: notification.statut,
          dateRdv: notification.dateRdv,
        } as any
      })),
      catchError(err => { console.error('WebSocket error:', err); return of(); })
    ),
  { functional: true }
);

