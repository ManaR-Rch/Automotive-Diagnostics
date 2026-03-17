import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = '/api';

  constructor(private http: HttpClient) { }

  // Services API
  getServices(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/services`);
  }

  getServiceById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/services/${id}`);
  }

  createService(service: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/services`, service);
  }

  updateService(id: number, service: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/services/${id}`, service);
  }

  deleteService(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/services/${id}`);
  }

  // Vehicles API
  getVehicles(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/vehicules`);
  }

  getVehicleById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/vehicules/${id}`);
  }

  createVehicle(vehicle: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/vehicules`, vehicle);
  }

  updateVehicle(id: number, vehicle: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/vehicules/${id}`, vehicle);
  }

  deleteVehicle(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/vehicules/${id}`);
  }

  // Appointments API
  getAppointments(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/rendez-vous`);
  }

  getAppointmentById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/rendez-vous/${id}`);
  }

  createAppointment(appointment: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/rendez-vous`, appointment);
  }

  updateAppointment(id: number, appointment: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/rendez-vous/${id}`, appointment);
  }

  deleteAppointment(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/rendez-vous/${id}`);
  }

  // Users API
  getUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/users`);
  }

  getUserById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/users/${id}`);
  }

  createUser(user: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/users`, user);
  }

  updateUser(id: number, user: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/users/${id}`, user);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/users/${id}`);
  }

  // Quotes (Devis) API
  getQuotes(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/devis`);
  }

  getQuoteById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/devis/${id}`);
  }

  createQuote(quote: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/devis`, quote);
  }

  updateQuote(id: number, quote: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/devis/${id}`, quote);
  }

  deleteQuote(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/devis/${id}`);
  }
}

