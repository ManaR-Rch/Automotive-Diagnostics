import { Injectable, PLATFORM_ID, inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuthResponse, LoginRequest, RegisterRequest, User } from '../core/models/user.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly API = '/api/auth';
  private readonly isBrowser = isPlatformBrowser(inject(PLATFORM_ID));

  constructor(private http: HttpClient) {}

  login(req: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.API}/login`, req).pipe(
      tap(res => {
        console.log('[AuthService.login] Response received:', res);
        if (this.isBrowser) {
          localStorage.setItem('token', res.token);
          localStorage.setItem('user', JSON.stringify({
            email: res.email, nom: res.nom, prenom: res.prenom, role: res.role
          }));
          console.log('[AuthService.login] Token stored in localStorage');
        } else {
          console.log('[AuthService.login] SSR mode - token not stored');
        }
      })
    );
  }

  register(req: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.API}/register`, req);
  }

  getProfile(): Observable<User> {
    return this.http.get<User>(`${this.API}/profile`);
  }

  updateProfile(data: Partial<User>): Observable<User> {
    return this.http.put<User>(`${this.API}/profile`, data);
  }

  changePassword(data: { motDePasseActuel: string; nouveauMotDePasse: string }): Observable<{ message: string }> {
    return this.http.put<{ message: string }>(`${this.API}/password`, data);
  }

  logout(): void {
    if (this.isBrowser) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
    }
  }

  isAuthenticated(): boolean {
    return this.isBrowser ? !!localStorage.getItem('token') : false;
  }

  isAdmin(): boolean {
    const user = this.getCurrentUser();
    return user?.role === 'ADMIN';
  }

  getCurrentUser(): { email: string; nom: string; prenom: string; role: string } | null {
    if (!this.isBrowser) return null;
    const u = localStorage.getItem('user');
    return u ? JSON.parse(u) : null;
  }
}

