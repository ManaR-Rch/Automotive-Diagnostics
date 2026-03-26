import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { Store } from '@ngrx/store';
import { selectIsAdmin } from '../../store/auth/auth.selectors';
import { combineLatest, Observable, Subject, takeUntil } from 'rxjs';
import { Appointment } from '../../core/models/appointment.model';
import { AdminActions } from '../../store/admin/admin.actions';
import { selectAdminAppointments, selectAdminLoading } from '../../store/admin/admin.selectors';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.component.html',
})
export class SidebarComponent implements OnInit, OnDestroy {
  private static readonly DISMISSED_NOTIFICATIONS_KEY = 'admin.dismissedAppointmentNotificationIds';

  @Input() collapsed = false;
  isAdmin$: Observable<boolean>;
  appointments$: Observable<Appointment[]>;
  adminLoading$: Observable<boolean>;

  unreadNotifications = 0;
  showRealtimeToast = false;
  latestNotificationText = '';

  private destroy$ = new Subject<void>();
  private initializedAppointments = false;
  private waitingInitialAdminLoad = false;
  private knownAppointmentIds = new Set<number>();
  private latestNotificationAppointmentId: number | null = null;
  private dismissedNotificationIds = new Set<number>();

  constructor(private store: Store, private router: Router) {
    this.isAdmin$ = this.store.select(selectIsAdmin);
    this.appointments$ = this.store.select(selectAdminAppointments);
    this.adminLoading$ = this.store.select(selectAdminLoading);
  }

  ngOnInit(): void {
    this.dismissedNotificationIds = this.loadDismissedNotificationIds();

    this.isAdmin$.pipe(takeUntil(this.destroy$)).subscribe(isAdmin => {
      if (isAdmin) {
        this.waitingInitialAdminLoad = true;
        this.store.dispatch(AdminActions.loadAllAppointments());
      }
    });

    combineLatest([this.appointments$, this.adminLoading$])
      .pipe(takeUntil(this.destroy$))
      .subscribe(([appointments, loading]) => {
        this.handleRealtimeAppointments(appointments, loading);
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  clearNotifications(): void {
    if (this.latestNotificationAppointmentId != null) {
      this.dismissedNotificationIds.add(this.latestNotificationAppointmentId);
      this.persistDismissedNotificationIds();
      this.latestNotificationAppointmentId = null;
    }

    this.unreadNotifications = 0;
    this.showRealtimeToast = false;
  }

  goToAdminAppointments(): void {
    this.clearNotifications();
    this.router.navigate(['/admin/appointments']);
  }

  private handleRealtimeAppointments(appointments: Appointment[], loading: boolean): void {
    const currentIds = new Set<number>();
    const newAppointments: Appointment[] = [];

    for (const appointment of appointments) {
      const appointmentId = this.toNormalizedId(appointment.id);
      if (appointmentId != null) {
        currentIds.add(appointmentId);
        if (this.initializedAppointments && !this.knownAppointmentIds.has(appointmentId)) {
          newAppointments.push(appointment);
        }
      }
    }

    if (this.waitingInitialAdminLoad) {
      if (!loading) {
        this.knownAppointmentIds = currentIds;
        this.initializedAppointments = true;
        this.waitingInitialAdminLoad = false;
      }
      return;
    }

    this.knownAppointmentIds = currentIds;

    if (!this.initializedAppointments) {
      this.initializedAppointments = true;
      return;
    }

    const visibleNewAppointments = newAppointments.filter(appointment => {
      const appointmentId = this.toNormalizedId(appointment.id);
      return appointmentId != null && !this.dismissedNotificationIds.has(appointmentId);
    });

    if (visibleNewAppointments.length > 0) {
      const latest = visibleNewAppointments[visibleNewAppointments.length - 1] as any;
      const clientName = latest.usuarioNom || latest.userName || 'Client';
      const date = latest.dateRdv || 'date inconnue';
      this.latestNotificationAppointmentId = this.toNormalizedId(latest.id);

      this.unreadNotifications = 1;
      this.latestNotificationText = `Nouveau RDV: ${clientName} (${date})`;
      this.showRealtimeToast = true;

      setTimeout(() => {
        this.showRealtimeToast = false;
      }, 5000);
    }
  }

  private loadDismissedNotificationIds(): Set<number> {
    if (typeof window === 'undefined') {
      return new Set<number>();
    }

    try {
      const stored = window.localStorage.getItem(SidebarComponent.DISMISSED_NOTIFICATIONS_KEY);
      if (!stored) {
        return new Set<number>();
      }

      const ids = JSON.parse(stored) as number[];
      if (!Array.isArray(ids)) {
        return new Set<number>();
      }

      const validIds = ids
        .map(id => this.toNormalizedId(id))
        .filter((id): id is number => id != null);

      return new Set<number>(validIds);
    } catch {
      return new Set<number>();
    }
  }

  private persistDismissedNotificationIds(): void {
    if (typeof window === 'undefined') {
      return;
    }

    try {
      const ids = Array.from(this.dismissedNotificationIds).slice(-200);
      window.localStorage.setItem(
        SidebarComponent.DISMISSED_NOTIFICATIONS_KEY,
        JSON.stringify(ids)
      );
    } catch {
      // Ignore storage errors to avoid breaking UI interactions.
    }
  }

  private toNormalizedId(value: unknown): number | null {
    if (typeof value === 'number') {
      return Number.isInteger(value) && value > 0 ? value : null;
    }

    if (typeof value === 'string' && value.trim() !== '') {
      const parsed = Number(value);
      return Number.isInteger(parsed) && parsed > 0 ? parsed : null;
    }

    return null;
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
