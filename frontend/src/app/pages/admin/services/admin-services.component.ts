import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { SidebarComponent } from '../../../components/sidebar/sidebar.component';
import { Service } from '../../../core/models/service.model';
import { AdminActions } from '../../../store/admin/admin.actions';
import { selectAdminServices, selectAdminLoading, selectAdminError } from '../../../store/admin/admin.selectors';

@Component({
  selector: 'app-admin-services',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, SidebarComponent],
  templateUrl: './admin-services.component.html'
})
export class AdminServicesComponent implements OnInit {
  services$: Observable<Service[]>;
  loading$: Observable<boolean>;
  error$: Observable<string | null>;
  showForm = false;
  editId: number | null = null;

  form: FormGroup;

  constructor(private store: Store, private fb: FormBuilder) {
    this.services$ = this.store.select(selectAdminServices);
    this.loading$ = this.store.select(selectAdminLoading);
    this.error$ = this.store.select(selectAdminError);
    this.form = this.fb.group({
      nom: ['', Validators.required],
      description: ['', Validators.required],
      categorie: ['', Validators.required],
      dureeEstimee: [60, Validators.required],
      prixMin: [0, Validators.required],
      prixMax: [0, Validators.required],
      actif: [true],
      ordreAffichage: [0],
    });
  }

  ngOnInit(): void {
    this.store.dispatch(AdminActions.loadAdminServices());
  }

  openCreate(): void {
    this.editId = null;
    this.showForm = true;
    this.form.reset({ dureeEstimee: 60, prixMin: 0, prixMax: 0, actif: true, ordreAffichage: 0 } as any);
  }

  openEdit(service: Service): void {
    this.editId = service.id || null;
    this.showForm = true;
    this.form.patchValue(service as any);
  }

  save(): void {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    const payload = this.form.value as Service;
    if (this.editId) this.store.dispatch(AdminActions.updateAdminService({ id: this.editId, service: payload }));
    else this.store.dispatch(AdminActions.createAdminService({ service: payload }));
    this.showForm = false;
  }

  delete(id: number): void {
    this.store.dispatch(AdminActions.deleteAdminService({ id }));
  }
}
