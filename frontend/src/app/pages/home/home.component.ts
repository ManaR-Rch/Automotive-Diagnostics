import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable, Subject, takeUntil } from 'rxjs';
import { ServicesActions } from '../../store/services/services.actions';
import { selectAllServices, selectServicesLoading } from '../../store/services/services.selectors';
import { selectIsAuthenticated, selectCurrentUser } from '../../store/auth/auth.selectors';
import { Service } from '../../core/models/service.model';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import { FooterComponent } from '../../components/footer/footer.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, NavbarComponent, FooterComponent],
  templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit, OnDestroy {
  services$: Observable<Service[]>;
  loading$: Observable<boolean>;
  isAuth$: Observable<boolean>;
  
  private destroy$ = new Subject<void>();

  contact = { name: '', email: '', message: '' };
  contactSent = false;

  stats = [
    { value: '250+', label: 'Projets Réalisés' },
    { value: '1.5K', label: 'Clients Satisfaits' },
    { value: '15+', label: 'Années d\'Expérience' },
    { value: '98%', label: 'Taux de Satisfaction' },
  ];

  serviceIcons: Record<string, string> = {
    'DIAGNOSTIC': '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 3H5a2 2 0 00-2 2v4m6-6h10a2 2 0 012 2v4M9 3v18m0 0h10a2 2 0 002-2v-4M9 21H5a2 2 0 01-2-2v-4m0 0h18"/>',
    'REPARATION': '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"/><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>',
  };

  constructor(private store: Store, private router: Router) {
    this.services$ = this.store.select(selectAllServices);
    this.loading$ = this.store.select(selectServicesLoading);
    this.isAuth$ = this.store.select(selectIsAuthenticated);
  }

  ngOnInit() {
    this.store.select(selectCurrentUser).pipe(takeUntil(this.destroy$)).subscribe(user => {
      if (user?.role === 'ADMIN') {
        this.router.navigate(['/admin/dashboard']);
      }
    });
    this.store.dispatch(ServicesActions.loadServices());
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  sendContact(e: Event) {
    e.preventDefault();
    this.contactSent = true;
    this.contact = { name: '', email: '', message: '' };
  }
}
