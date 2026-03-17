export interface User {
  id?: number;
  nom: string;
  prenom: string;
  email: string;
  telephone?: string;
  role: 'CLIENT' | 'ADMIN';
  actif?: boolean;
  dateCreation?: string;
}

export interface AuthResponse {
  token: string;
  type: string;
  email: string;
  nom: string;
  prenom: string;
  role: string;
}

export interface LoginRequest {
  email: string;
  motDePasse: string;
}

export interface RegisterRequest {
  nom: string;
  prenom: string;
  email: string;
  motDePasse: string;
  telephone?: string;
}
