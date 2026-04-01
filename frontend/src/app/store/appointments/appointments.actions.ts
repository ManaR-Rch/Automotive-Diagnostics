import { createActionGroup, emptyProps, props } from '@ngrx/store';
import { Appointment } from '../../core/models/appointment.model';

export const AppointmentsActions = createActionGroup({
  source: 'Appointments',
  events: {
    'Load My Appointments': emptyProps(),
    'Load My Appointments Success': props<{ appointments: Appointment[] }>(),
    'Load My Appointments Failure': props<{ error: string }>(),
    'Create Appointment':   props<{ appointment: Appointment }>(),
    'Create Appointment Success': props<{ appointment: Appointment }>(),
    'Create Appointment Failure': props<{ error: string }>(),
    'Cancel Appointment':   props<{ id: number }>(),
    'Cancel Appointment Success': props<{ appointment: Appointment }>(),
    'Cancel Appointment Failure': props<{ error: string }>(),
    'Clear Error':          emptyProps(),
    'WebSocket Client New Appointment': props<{ appointment: Appointment }>(),
    'WebSocket Client Update Appointment': props<{ appointment: Appointment }>(),
  }
});
