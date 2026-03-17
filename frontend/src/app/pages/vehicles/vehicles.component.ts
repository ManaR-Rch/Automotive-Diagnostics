import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { SidebarComponent } from '../../components/sidebar/sidebar.component';
import { Vehicle } from '../../core/models/vehicle.model';
import { VehiclesActions } from '../../store/vehicles/vehicles.actions';
import { selectAllVehicles, selectVehiclesLoading, selectVehiclesError } from '../../store/vehicles/vehicles.selectors';

@Component({
  selector: 'app-vehicles',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, SidebarComponent],
  templateUrl: './vehicles.component.html',
  styleUrl: './vehicles.component.css'
})
export class VehiclesComponent implements OnInit {
  vehicles$: Observable<Vehicle[]>;
  loading$: Observable<boolean>;
  error$: Observable<string | null>;

  showModal = false;
  editMode = false;
  selectedId: number | null = null;
  deleteConfirmId: number | null = null;

  form: FormGroup;

  carburantOptions = ['ESSENCE', 'DIESEL', 'HYBRIDE', 'ELECTRIQUE', 'GPL'];

  constructor(private store: Store, private fb: FormBuilder) {
    this.vehicles$ = this.store.select(selectAllVehicles);
    this.loading$ = this.store.select(selectVehiclesLoading);
    this.error$ = this.store.select(selectVehiclesError);

    this.form = this.fb.group({
      marque: ['', [Validators.required, Validators.minLength(2)]],
      modele: ['', [Validators.required, Validators.minLength(1)]],
      annee: ['', [Validators.required, Validators.min(1900), Validators.max(new Date().getFullYear() + 1)]],
      carburant: ['ESSENCE'],
      kilometrage: [0, [Validators.min(0)]],
      vin: [''],
      estPrincipal: [false]
    });
  }

  ngOnInit(): void {
    this.store.dispatch(VehiclesActions.loadMyVehicles());
  }

  openCreate(): void {
    this.editMode = false;
    this.selectedId = null;
    this.form.reset({ carburant: 'ESSENCE', kilometrage: 0, estPrincipal: false });
    this.showModal = true;
  }

  openEdit(v: Vehicle): void {
    this.editMode = true;
    this.selectedId = v.id!;
    this.form.patchValue(v);
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.form.reset();
  }

  submit(): void {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    if (this.editMode && this.selectedId) {
      this.store.dispatch(VehiclesActions.updateVehicle({ id: this.selectedId, vehicle: this.form.value }));
    } else {
      this.store.dispatch(VehiclesActions.createVehicle({ vehicle: this.form.value }));
    }
    this.closeModal();
  }

  confirmDelete(id: number): void {
    this.deleteConfirmId = id;
  }

  deleteVehicle(): void {
    if (this.deleteConfirmId) {
      this.store.dispatch(VehiclesActions.deleteVehicle({ id: this.deleteConfirmId }));
      this.deleteConfirmId = null;
    }
  }

  field(name: string) { return this.form.get(name); }
}
