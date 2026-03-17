import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { Service } from '../../core/models/service.model';
import { ServicesActions } from '../../store/services/services.actions';
import { selectAllServices, selectServicesLoading } from '../../store/services/services.selectors';

@Component({
  selector: 'app-services',
  standalone: true,
  imports: [CommonModule, RouterModule, NavbarComponent, FooterComponent],
  templateUrl: './services.component.html',
  styleUrl: './services.component.css'
})
export class ServicesComponent implements OnInit {
  services$: Observable<Service[]>;
  loading$: Observable<boolean>;
  activeCategory = 'TOUS';

  categories = ['TOUS', 'DIAGNOSTIC', 'ENTRETIEN', 'REPARATION', 'ELECTRICITE', 'CLIMATISATION', 'CARROSSERIE', 'PNEUMATIQUES'];

  constructor(private store: Store) {
    this.services$ = this.store.select(selectAllServices);
    this.loading$ = this.store.select(selectServicesLoading);
  }

  ngOnInit(): void {
    this.store.dispatch(ServicesActions.loadServices());
  }

  setCategory(cat: string): void {
    this.activeCategory = cat;
  }

  getDurationLabel(minutes: number): string {
    if (!minutes) return '—';
    if (minutes < 60) return `${minutes} min`;
    const h = Math.floor(minutes / 60);
    const m = minutes % 60;
    return m ? `${h}h${m.toString().padStart(2, '0')}` : `${h}h`;
  }
}
