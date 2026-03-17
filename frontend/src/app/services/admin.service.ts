import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../core/models/user.model';
import { Appointment, DashboardStats } from '../core/models/appointment.model';
import { Service } from '../core/models/service.model';

@Injectable({ providedIn: 'root' })
export class AdminService {
  private readonly API = '/api/admin';

  constructor(private http: HttpClient) {}

  getStats(): Observable<DashboardStats> {
    return this.http.get<DashboardStats>(`${this.API}/stats`);
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.API}/users`);
  }

  createUser(user: User & { motDePasse: string }): Observable<User> {
    return this.http.post<User>(`${this.API}/users`, user);
  }

  updateUser(id: number, user: User): Observable<User> {
    return this.http.put<User>(`${this.API}/users/${id}`, user);
  }

  toggleUser(id: number): Observable<User> {
    return this.http.put<User>(`${this.API}/users/${id}/toggle`, {});
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API}/users/${id}`);
  }

  getAllAppointments(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.API}/rendezvous`);
  }

  updateAppointmentStatus(id: number, statut: string, notes?: string): Observable<Appointment> {
    return this.http.put<Appointment>(`${this.API}/rendezvous/${id}/statut`, { statut, notesAdmin: notes });
  }

  getAllServices(): Observable<Service[]> {
    return this.http.get<Service[]>(`${this.API}/services`);
  }

  createService(s: Service): Observable<Service> {
    return this.http.post<Service>(`${this.API}/services`, s);
  }

  updateService(id: number, s: Service): Observable<Service> {
    return this.http.put<Service>(`${this.API}/services/${id}`, s);
  }

  deleteService(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API}/services/${id}`);
  }
}

