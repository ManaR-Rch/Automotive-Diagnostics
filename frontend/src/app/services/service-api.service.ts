import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Service } from '../core/models/service.model';

@Injectable({ providedIn: 'root' })
export class ServiceApiService {
  private readonly API = '/api/services';

  constructor(private http: HttpClient) {}

  getActiveServices(): Observable<Service[]> {
    return this.http.get<Service[]>(this.API);
  }

  getAllServices(): Observable<Service[]> {
    return this.http.get<Service[]>(`${this.API}/all`);
  }

  getServiceById(id: number): Observable<Service> {
    return this.http.get<Service>(`${this.API}/${id}`);
  }

  createService(s: Service): Observable<Service> {
    return this.http.post<Service>(this.API, s);
  }

  updateService(id: number, s: Service): Observable<Service> {
    return this.http.put<Service>(`${this.API}/${id}`, s);
  }

  deleteService(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API}/${id}`);
  }
}

