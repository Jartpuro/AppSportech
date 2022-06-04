import { ISchedule } from 'app/entities/schedule/schedule.model';
import { State } from 'app/entities/enumerations/state.model';

export interface IModality {
  id?: number;
  modalityName?: string;
  modalityColor?: string;
  modalityState?: State;
  schedules?: ISchedule[] | null;
}

export class Modality implements IModality {
  constructor(
    public id?: number,
    public modalityName?: string,
    public modalityColor?: string,
    public modalityState?: State,
    public schedules?: ISchedule[] | null
  ) {}
}

export function getModalityIdentifier(modality: IModality): number | undefined {
  return modality.id;
}
