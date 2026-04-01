import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { Store } from '@ngrx/store';
import { selectIsAdmin } from '../../store/auth/auth.selectors';
import { Observable, Subject, takeUntil } from 'rxjs';
import { Actions, ofType } from '@ngrx/effects';
import { AuthActions } from '../../store/auth/auth.actions';
import { AdminActions } from '../../store/admin/admin.actions';
import { AppointmentsActions } from '../../store/appointments/appointments.actions';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.component.html',
})
export class SidebarComponent implements OnInit, OnDestroy {
  @Input() collapsed = false;
  isAdmin$: Observable<boolean>;
  
  unreadNotifications = 0;
  showRealtimeToast = false;
  latestNotificationText = '';
  private adminMode = false;
  
  private destroy$ = new Subject<void>();

  constructor(private store: Store, private router: Router, private actions$: Actions) {
    this.isAdmin$ = this.store.select(selectIsAdmin);
  }

  ngOnInit(): void {
    this.isAdmin$.pipe(takeUntil(this.destroy$)).subscribe(isAdmin => {
      this.adminMode = isAdmin;
      if (isAdmin) {
        this.store.dispatch(AdminActions.loadAllAppointments());
      } else {
        this.store.dispatch(AppointmentsActions.loadMyAppointments());
      }
    });

    this.actions$.pipe(
      ofType(AdminActions.webSocketNewAppointment),
      takeUntil(this.destroy$)
    ).subscribe(({ appointment }) => {
      if (this.adminMode) {
        const clientName = (appointment as any).usuarioNom || (appointment as any).userName || 'Client';
        const date = appointment.dateRdv || 'date inconnue';
        this.showPopup(`Nouveau RDV: ${clientName} (${date})`);
      }
    });

    this.actions$.pipe(
      ofType(AdminActions.webSocketUpdateAppointment),
      takeUntil(this.destroy$)
    ).subscribe(({ appointment }) => {
      if (this.adminMode) {
        this.showPopup(`RDV (${appointment.id}) mis à jour : ${appointment.statut}`);
      }
    });

    this.actions$.pipe(
      ofType(AppointmentsActions.webSocketClientUpdateAppointment),
      takeUntil(this.destroy$)
    ).subscribe(({ appointment }) => {
      if (!this.adminMode) {
        this.showPopup(`Votre RDV est mis à jour : ${appointment.statut}`);
      }
    });
  }

  private showPopup(text: string) {
    this.unreadNotifications++;
    this.latestNotificationText = text;
    this.showRealtimeToast = true;
    setTimeout(() => {
      this.showRealtimeToast = false;
    }, 5000);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  clearNotifications(): void {
    this.unreadNotifications = 0;
    this.showRealtimeToast = false;
  }

  goToAdminAppointments(): void {
    this.clearNotifications();
    this.router.navigate(this.adminMode ? ['/admin/appointments'] : ['/appointments']);
  }

  logout(): void {
    this.store.dispatch(AuthActions.logout());
  }

  navItems = [
    { label: 'Dashboard', icon: 'grid', route: '/dashboard' },
    { label: 'Mes Véhicules', icon: 'car', route: '/vehicles' },
    { label: 'Services', icon: 'wrench', route: '/services' },
    { label: 'Rendez-vous', icon: 'calendar', route: '/appointments' },
    { label: 'Profil', icon: 'user', route: '/profile' },
  ];

  adminNavItems = [
    { label: 'Profil', icon: 'user', route: '/profile' },
  ];

  adminItems = [
    { label: 'Stats Admin', icon: 'chart', route: '/admin/dashboard' },
    { label: 'Utilisateurs', icon: 'users', route: '/admin/users' },
    { label: 'Services', icon: 'gear', route: '/admin/services' },
    { label: 'Rendez-vous', icon: 'calendar-check', route: '/admin/appointments' },
  ];
}
