import { inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, switchMap } from 'rxjs/operators';
import { of } from 'rxjs';
import { AdminActions } from './admin.actions';
import { AdminService } from '../../services/admin.service';

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
      switchMap(({ user }) => svc.createUser(user).pipe(
        map(u => AdminActions.createUserSuccess({ user: u })),
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
        catchError(err => of(AdminActions.loadAllAppointmentsFailure({ error: err.message })))
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
        catchError(err => of(AdminActions.updateAppointmentStatusFailure({ error: err.message })))
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
        catchError(err => of(AdminActions.createAdminServiceFailure({ error: err.message })))
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
        catchError(err => of(AdminActions.updateAdminServiceFailure({ error: err.message })))
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
        catchError(err => of(AdminActions.deleteAdminServiceFailure({ error: err.message })))
      ))
    ),
  { functional: true }
);
