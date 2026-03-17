import { createActionGroup, emptyProps, props } from '@ngrx/store';
import { Service } from '../../core/models/service.model';

export const ServicesActions = createActionGroup({
  source: 'Services',
  events: {
    'Load Services':    emptyProps(),
    'Load Services Success': props<{ services: Service[] }>(),
    'Load Services Failure': props<{ error: string }>(),
    'Load All Services': emptyProps(),
    'Load All Services Success': props<{ services: Service[] }>(),
    'Load All Services Failure': props<{ error: string }>(),
    'Create Service':   props<{ service: Service }>(),
    'Create Service Success': props<{ service: Service }>(),
    'Create Service Failure': props<{ error: string }>(),
    'Update Service':   props<{ id: number; service: Service }>(),
    'Update Service Success': props<{ service: Service }>(),
    'Update Service Failure': props<{ error: string }>(),
    'Delete Service':   props<{ id: number }>(),
    'Delete Service Success': props<{ id: number }>(),
    'Delete Service Failure': props<{ error: string }>(),
  }
});
