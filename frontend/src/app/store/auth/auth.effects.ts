import { inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import { AuthActions } from './auth.actions';
import { AuthService } from '../../services/auth.service';

export const loginEffect = createEffect(
  (actions$ = inject(Actions), authService = inject(AuthService), router = inject(Router)) =>
    actions$.pipe(
      ofType(AuthActions.login),
      switchMap(({ request }) =>
        authService.login(request).pipe(
          tap(res => {
            const isAdmin = res.role === 'ADMIN';
            router.navigate([isAdmin ? '/admin/dashboard' : '/dashboard']);
          }),
          map(response => AuthActions.loginSuccess({ response })),
          catchError(err => of(AuthActions.loginFailure({ error: err.error?.message || (err.status === 0 ? 'Impossible de joindre le serveur' : 'Échec de connexion') })))
        )
      )
    ),
  { functional: true }
);

export const registerEffect = createEffect(
  (actions$ = inject(Actions), authService = inject(AuthService)) =>
    actions$.pipe(
      ofType(AuthActions.register),
      switchMap(({ request }) =>
        authService.register(request).pipe(
          map(response => AuthActions.registerSuccess({ response })),
          catchError(err => {
            const msg = err.error?.message
              || err.error?.error
              || (err.status === 0 ? 'Impossible de joindre le serveur' : null)
              || 'Échec d\'inscription';
            return of(AuthActions.registerFailure({ error: msg }));
          })
        )
      )
    ),
  { functional: true }
);

export const loadProfileEffect = createEffect(
  (actions$ = inject(Actions), authService = inject(AuthService)) =>
    actions$.pipe(
      ofType(AuthActions.loadProfile),
      switchMap(() =>
        authService.getProfile().pipe(
          map(user => AuthActions.loadProfileSuccess({ user })),
          catchError(err => of(AuthActions.loadProfileFailure({ error: err.message })))
        )
      )
    ),
  { functional: true }
);

export const logoutEffect = createEffect(
  (actions$ = inject(Actions), authService = inject(AuthService), router = inject(Router)) =>
    actions$.pipe(
      ofType(AuthActions.logout),
      tap(() => { authService.logout(); router.navigate(['/']); })
    ),
  { functional: true, dispatch: false }
);
