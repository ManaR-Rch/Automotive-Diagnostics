import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Vehicle } from '../core/models/vehicle.model';

@Injectable({ providedIn: 'root' })
export class VehicleService {
  private readonly API = '/api/vehicules';

  constructor(private http: HttpClient) {}

  getMyVehicles(): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(`${this.API}/mes-vehicules`);
  }

  getVehicleById(id: number): Observable<Vehicle> {
    return this.http.get<Vehicle>(`${this.API}/${id}`);
  }

  createVehicle(v: Vehicle): Observable<Vehicle> {
    return this.http.post<Vehicle>(this.API, v);
  }

  updateVehicle(id: number, v: Vehicle): Observable<Vehicle> {
    return this.http.put<Vehicle>(`${this.API}/${id}`, v);
  }

  deleteVehicle(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API}/${id}`);
  }
}

