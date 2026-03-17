import { createReducer, on } from '@ngrx/store';
import { ServicesActions } from './services.actions';
import { Service } from '../../core/models/service.model';

export interface ServicesState {
  services: Service[];
  loading: boolean;
  error: string | null;
}

const initialState: ServicesState = { services: [], loading: false, error: null };

export const servicesReducer = createReducer(
  initialState,
  on(ServicesActions.loadServices, ServicesActions.loadAllServices,
    ServicesActions.createService, ServicesActions.updateService, ServicesActions.deleteService,
    s => ({ ...s, loading: true, error: null })),
  on(ServicesActions.loadServicesSuccess, ServicesActions.loadAllServicesSuccess,
    (s, { services }) => ({ ...s, loading: false, services })),
  on(ServicesActions.createServiceSuccess, (s, { service }) => ({ ...s, loading: false, services: [...s.services, service] })),
  on(ServicesActions.updateServiceSuccess, (s, { service }) => ({
    ...s, loading: false,
    services: s.services.map(sv => sv.id === service.id ? service : sv)
  })),
  on(ServicesActions.deleteServiceSuccess, (s, { id }) => ({
    ...s, loading: false, services: s.services.filter(sv => sv.id !== id)
  })),
  on(ServicesActions.loadServicesFailure, ServicesActions.loadAllServicesFailure,
    ServicesActions.createServiceFailure, ServicesActions.updateServiceFailure, ServicesActions.deleteServiceFailure,
    (s, { error }) => ({ ...s, loading: false, error })),
);
