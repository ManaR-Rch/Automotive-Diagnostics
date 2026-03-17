import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideStore } from '@ngrx/store';
import { provideEffects } from '@ngrx/effects';
import { provideStoreDevtools } from '@ngrx/store-devtools';

import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { authReducer } from './store/auth/auth.reducer';
import { vehiclesReducer } from './store/vehicles/vehicles.reducer';
import { servicesReducer } from './store/services/services.reducer';
import { appointmentsReducer } from './store/appointments/appointments.reducer';
import { adminReducer } from './store/admin/admin.reducer';
import { authInterceptor } from './core/interceptors/auth.interceptor';
import { loginEffect, registerEffect, loadProfileEffect, logoutEffect } from './store/auth/auth.effects';
import { loadVehiclesEffect, createVehicleEffect, updateVehicleEffect, deleteVehicleEffect } from './store/vehicles/vehicles.effects';
import { loadServicesEffect, loadAllServicesEffect, createServiceEffect, updateServiceEffect, deleteServiceEffect } from './store/services/services.effects';
import { loadAppointmentsEffect, createAppointmentEffect, cancelAppointmentEffect } from './store/appointments/appointments.effects';
import {
  loadStatsEffect,
  loadUsersEffect,
  createUserEffect,
  updateUserEffect,
  toggleUserEffect,
  deleteUserEffect,
  loadAllAppointmentsEffect,
  updateAppointmentStatusEffect,
  loadAdminServicesEffect,
  createAdminServiceEffect,
  updateAdminServiceEffect,
  deleteAdminServiceEffect,
} from './store/admin/admin.effects';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }), 
    provideRouter(routes), 
    provideClientHydration(withEventReplay()),
    provideHttpClient(withInterceptors([authInterceptor])),
    provideStore({
      auth: authReducer,
      vehicles: vehiclesReducer,
      services: servicesReducer,
      appointments: appointmentsReducer,
      admin: adminReducer,
    }),
    provideEffects({
      loginEffect,
      registerEffect,
      loadProfileEffect,
      logoutEffect,
      loadVehiclesEffect,
      createVehicleEffect,
      updateVehicleEffect,
      deleteVehicleEffect,
      loadServicesEffect,
      loadAllServicesEffect,
      createServiceEffect,
      updateServiceEffect,
      deleteServiceEffect,
      loadAppointmentsEffect,
      createAppointmentEffect,
      cancelAppointmentEffect,
      loadStatsEffect,
      loadUsersEffect,
      createUserEffect,
      updateUserEffect,
      toggleUserEffect,
      deleteUserEffect,
      loadAllAppointmentsEffect,
      updateAppointmentStatusEffect,
      loadAdminServicesEffect,
      createAdminServiceEffect,
      updateAdminServiceEffect,
      deleteAdminServiceEffect,
    }),
    provideStoreDevtools({ maxAge: 25 }),
  ]
};
