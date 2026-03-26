import { inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, switchMap, concatMap } from 'rxjs/operators';
import { of } from 'rxjs';
import { Store } from '@ngrx/store';
import { filter, withLatestFrom } from 'rxjs/operators';
import { AdminActions } from './admin.actions';
import { AdminService } from '../../services/admin.service';
import { WebSocketService } from '../../core/services/websocket.service';
import { selectIsAdmin } from '../auth/auth.selectors';

export const loadStatsEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(AdminService)) =>
    actions$.pipe(
      ofType(AdminActions.loadStats),
      switchMap(() => svc.getStats().pipe(
        map(stats => AdminActions.loadStatsSuccess({ stats })),
        catchError(err => of(AdminActions.loadStatsFailure({ error: err.message })))
      ))
    ),
  { functional: true }
);

export const loadUsersEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(AdminService)) =>
    actions$.pipe(
      ofType(AdminActions.loadUsers),
      switchMap(() => svc.getAllUsers().pipe(
        map(users => AdminActions.loadUsersSuccess({ users })),
        catchError(err => of(AdminActions.loadUsersFailure({ error: err.message })))
      ))
    ),
  { functional: true }
);

export const createUserEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(AdminService)) =>
    actions$.pipe(
      ofType(AdminActions.createUser),
      concatMap(({ user }) => svc.createUser(user).pipe(
        concatMap(u => of(AdminActions.createUserSuccess({ user: u }), AdminActions.loadUsers())),
        catchError(err => of(AdminActions.createUserFailure({ error: err.error?.message || err.message })))
      ))
    ),
  { functional: true }
);

export const updateUserEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(AdminService)) =>
    actions$.pipe(
      ofType(AdminActions.updateUser),
      switchMap(({ id, user }) => svc.updateUser(id, user).pipe(
        map(u => AdminActions.updateUserSuccess({ user: u })),
        catchError(err => of(AdminActions.updateUserFailure({ error: err.message })))
      ))
    ),
  { functional: true }
);

export const toggleUserEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(AdminService)) =>
    actions$.pipe(
      ofType(AdminActions.toggleUser),
      switchMap(({ id }) => svc.toggleUser(id).pipe(
        map(u => AdminActions.toggleUserSuccess({ user: u })),
        catchError(err => of(AdminActions.toggleUserFailure({ error: err.message })))
      ))
    ),
  { functional: true }
);

export const deleteUserEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(AdminService)) =>
    actions$.pipe(
      ofType(AdminActions.deleteUser),
      switchMap(({ id }) => svc.deleteUser(id).pipe(
        map(() => AdminActions.deleteUserSuccess({ id })),
        catchError(err => of(AdminActions.deleteUserFailure({ error: err.message })))
      ))
    ),
  { functional: true }
);

export const loadAllAppointmentsEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(AdminService)) =>
    actions$.pipe(
      ofType(AdminActions.loadAllAppointments),
      switchMap(() => svc.getAllAppointments().pipe(
        map(appointments => AdminActions.loadAllAppointmentsSuccess({ appointments })),
        catchError(err => of(AdminActions.loadAllAppointmentsFailure({
          error: err.error?.message || (err.status === 403
            ? 'Acces admin refuse. Veuillez vous reconnecter.'
            : 'Impossible de charger les rendez-vous admin.')
        })))
      ))
    ),
  { functional: true }
);

export const updateAppointmentStatusEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(AdminService)) =>
    actions$.pipe(
      ofType(AdminActions.updateAppointmentStatus),
      switchMap(({ id, statut, notes }) => svc.updateAppointmentStatus(id, statut, notes).pipe(
        map(a => AdminActions.updateAppointmentStatusSuccess({ appointment: a })),
        catchError(err => of(AdminActions.updateAppointmentStatusFailure({
          error: err.error?.message || (err.status === 403
            ? 'Action admin non autorisee.'
            : 'Impossible de mettre a jour le statut du rendez-vous.')
        })))
      ))
    ),
  { functional: true }
);

export const loadAdminServicesEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(AdminService)) =>
    actions$.pipe(
      ofType(AdminActions.loadAdminServices),
      switchMap(() => svc.getAllServices().pipe(
        map(services => AdminActions.loadAdminServicesSuccess({ services })),
        catchError(err => of(AdminActions.loadAdminServicesFailure({ error: err.message })))
      ))
    ),
  { functional: true }
);

export const createAdminServiceEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(AdminService)) =>
    actions$.pipe(
      ofType(AdminActions.createAdminService),
      switchMap(({ service }) => svc.createService(service).pipe(
        map(s => AdminActions.createAdminServiceSuccess({ service: s })),
        catchError(err => of(AdminActions.createAdminServiceFailure({
          error: err.error?.message || (err.status === 400
            ? 'Donnees service invalides. Verifiez les champs (notamment categorie).'
            : 'Impossible de creer le service.')
        })))
      ))
    ),
  { functional: true }
);

export const updateAdminServiceEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(AdminService)) =>
    actions$.pipe(
      ofType(AdminActions.updateAdminService),
      switchMap(({ id, service }) => svc.updateService(id, service).pipe(
        map(s => AdminActions.updateAdminServiceSuccess({ service: s })),
        catchError(err => of(AdminActions.updateAdminServiceFailure({
          error: err.error?.message || (err.status === 400
            ? 'Donnees service invalides. Verifiez les champs (notamment categorie).'
            : 'Impossible de mettre a jour le service.')
        })))
      ))
    ),
  { functional: true }
);

export const deleteAdminServiceEffect = createEffect(
  (actions$ = inject(Actions), svc = inject(AdminService)) =>
    actions$.pipe(
      ofType(AdminActions.deleteAdminService),
      switchMap(({ id }) => svc.deleteService(id).pipe(
        map(() => AdminActions.deleteAdminServiceSuccess({ id })),
        catchError(err => of(AdminActions.deleteAdminServiceFailure({
          error: err.error?.message || 'Impossible de supprimer le service.'
        })))
      ))
    ),
  { functional: true }
);

export const webSocketNewAppointmentEffect = createEffect(
  (ws = inject(WebSocketService), store = inject(Store)) =>
    ws.newRdv$.pipe(
      withLatestFrom(store.select(selectIsAdmin)),
      filter(([, isAdmin]) => isAdmin),
      map(([notification]) => AdminActions.webSocketNewAppointment({
        appointment: {
          id: notification.id,
          statut: notification.statut,
          dateRdv: notification.dateRdv,
          usuarioEmail: (notification as any).userEmail,
          usuarioNom: (notification as any).userName,
        } as any
      })),
      catchError(err => { console.error('WebSocket error:', err); return of(); })
    ),
  { functional: true }
);

export const webSocketUpdateAppointmentEffect = createEffect(
  (ws = inject(WebSocketService), store = inject(Store)) =>
    ws.updateRdv$.pipe(
      withLatestFrom(store.select(selectIsAdmin)),
      filter(([, isAdmin]) => isAdmin),
      map(([notification]) => AdminActions.webSocketUpdateAppointment({
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
