import { createReducer, on } from '@ngrx/store';
import { AuthActions } from './auth.actions';
import { User } from '../../core/models/user.model';

export interface AuthState {
  token: string | null;
  user: User | null;
  loading: boolean;
  error: string | null;
  registered: boolean;
}

const isBrowser = typeof localStorage !== 'undefined';
const stored = isBrowser ? localStorage.getItem('user') : null;
const initialState: AuthState = {
  token: isBrowser ? localStorage.getItem('token') : null,
  user: stored ? JSON.parse(stored) : null,
  loading: false,
  error: null,
  registered: false,
};

export const authReducer = createReducer(
  initialState,
  on(AuthActions.login, AuthActions.register, s => ({ ...s, loading: true, error: null, registered: false })),
  on(AuthActions.loginSuccess, (s, { response }) => ({
    ...s, loading: false,
    token: response.token,
    user: { email: response.email, nom: response.nom, prenom: response.prenom, role: response.role as any },
  })),
  on(AuthActions.loginFailure, AuthActions.registerFailure, (s, { error }) => ({ ...s, loading: false, error })),
  on(AuthActions.registerSuccess, s => ({ ...s, loading: false, registered: true })),
  on(AuthActions.loadProfileSuccess, (s, { user }) => ({ ...s, user })),
  on(AuthActions.logout, () => ({ ...initialState, token: null, user: null })),
  on(AuthActions.clearError, s => ({ ...s, error: null })),
);
