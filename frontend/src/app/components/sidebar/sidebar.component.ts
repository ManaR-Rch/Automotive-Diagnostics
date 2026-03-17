import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Store } from '@ngrx/store';
import { selectIsAdmin } from '../../store/auth/auth.selectors';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.component.html',
})
export class SidebarComponent {
  @Input() collapsed = false;
  isAdmin$: Observable<boolean>;

  constructor(private store: Store) {
    this.isAdmin$ = this.store.select(selectIsAdmin);
  }

  navItems = [
    { label: 'Dashboard', icon: 'grid', route: '/dashboard' },
    { label: 'Mes Véhicules', icon: 'car', route: '/vehicles' },
    { label: 'Services', icon: 'wrench', route: '/services' },
    { label: 'Rendez-vous', icon: 'calendar', route: '/appointments' },
    { label: 'Profil', icon: 'user', route: '/profile' },
  ];

  adminItems = [
    { label: 'Stats Admin', icon: 'chart', route: '/admin/dashboard' },
    { label: 'Utilisateurs', icon: 'users', route: '/admin/users' },
    { label: 'Services', icon: 'gear', route: '/admin/services' },
    { label: 'Rendez-vous', icon: 'calendar-check', route: '/admin/appointments' },
  ];
}
