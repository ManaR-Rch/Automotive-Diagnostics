import { HttpInterceptorFn } from '@angular/common/http';
import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);
  const isBrowser = isPlatformBrowser(inject(PLATFORM_ID));
  const token = isBrowser ? localStorage.getItem('token') : null;
  const isAuthEndpoint = req.url.includes('/api/auth/login') || req.url.includes('/api/auth/register');

  console.log(`[AUTH-INTERCEPTOR] Request to ${req.url}`);
  console.log(`[AUTH-INTERCEPTOR] Token present:`, !!token);
  if (token) {
    console.log(`[AUTH-INTERCEPTOR] Token: ${token.substring(0, 20)}...`);
  }

  const authReq = token
    ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
    : req;

  return next(authReq).pipe(
    catchError((error) => {
      if ((error.status === 401 || error.status === 403) && isBrowser && !isAuthEndpoint) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        router.navigate(['/auth/login']);
      }
      return throwError(() => error);
    })
  );
};
