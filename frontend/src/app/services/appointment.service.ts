import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Appointment } from '../core/models/appointment.model';

@Injectable({ providedIn: 'root' })
export class AppointmentService {
  private readonly API = '/api/rendezvous';

  constructor(private http: HttpClient) {}

  getMyAppointments(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.API}/mes-rendezvous`);
  }

  createAppointment(a: Appointment): Observable<Appointment> {
    return this.http.post<Appointment>(this.API, a);
  }

  cancelAppointment(id: number): Observable<Appointment> {
    return this.http.put<Appointment>(`${this.API}/${id}/annuler`, {});
  }
}

