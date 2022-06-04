import { ISchedule } from 'app/entities/schedule/schedule.model';
import { State } from 'app/entities/enumerations/state.model';

export interface IDay {
  id?: number;
  dayName?: string;
  dayState?: State;
  schedules?: ISchedule[] | null;
}

export class Day implements IDay {
  constructor(public id?: number, public dayName?: string, public dayState?: State, public schedules?: ISchedule[] | null) {}
}

export function getDayIdentifier(day: IDay): number | undefined {
  return day.id;
}
