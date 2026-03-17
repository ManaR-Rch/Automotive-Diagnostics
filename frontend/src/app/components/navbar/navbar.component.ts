import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { selectCurrentUser, selectIsAuthenticated, selectIsAdmin } from '../../store/auth/auth.selectors';
import { AuthActions } from '../../store/auth/auth.actions';
import { User } from '../../core/models/user.model';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
})
export class NavbarComponent implements OnInit {
  isAuthenticated$: Observable<boolean>;
  currentUser$: Observable<User | null>;
  isAdmin$: Observable<boolean>;
  mobileOpen = false;
  userMenuOpen = false;

  constructor(private store: Store) {
    this.isAuthenticated$ = this.store.select(selectIsAuthenticated);
    this.currentUser$     = this.store.select(selectCurrentUser);
    this.isAdmin$         = this.store.select(selectIsAdmin);
  }

  ngOnInit() {}

  logout() {
    this.store.dispatch(AuthActions.logout());
  }

  toggleMobile() { this.mobileOpen = !this.mobileOpen; }
  toggleUserMenu() { this.userMenuOpen = !this.userMenuOpen; }
}
