import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { SidebarComponent } from '../../../components/sidebar/sidebar.component';
import { DashboardStats } from '../../../core/models/appointment.model';
import { AdminActions } from '../../../store/admin/admin.actions';
import { selectAdminStats, selectAdminLoading } from '../../../store/admin/admin.selectors';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, SidebarComponent],
  templateUrl: './admin-dashboard.component.html'
})
export class AdminDashboardComponent implements OnInit {
  stats$: Observable<DashboardStats | null>;
  loading$: Observable<boolean>;

  constructor(private store: Store) {
    this.stats$ = this.store.select(selectAdminStats);
    this.loading$ = this.store.select(selectAdminLoading);
  }

  ngOnInit(): void {
    this.store.dispatch(AdminActions.loadStats());
  }
}
