import { CanActivateFn, Router } from '@angular/router';
import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

export const adminGuard: CanActivateFn = () => {
  const router = inject(Router);
  const isBrowser = isPlatformBrowser(inject(PLATFORM_ID));
  if (!isBrowser) { router.navigate(['/auth/login']); return false; }
  const userStr = localStorage.getItem('user');
  if (!userStr) { router.navigate(['/auth/login']); return false; }
  try {
    const user = JSON.parse(userStr);
    if (user.role === 'ADMIN') return true;
    router.navigate(['/dashboard']);
    return false;
  } catch {
    router.navigate(['/auth/login']);
    return false;
  }
};
