import { ISchedule } from 'app/entities/schedule/schedule.model';
import { State } from 'app/entities/enumerations/state.model';

export interface IScheduleVersion {
  id?: number;
  versionNumber?: string;
  versionState?: State;
  schedules?: ISchedule[] | null;
}

export class ScheduleVersion implements IScheduleVersion {
  constructor(public id?: number, public versionNumber?: string, public versionState?: State, public schedules?: ISchedule[] | null) {}
}

export function getScheduleVersionIdentifier(scheduleVersion: IScheduleVersion): number | undefined {
  return scheduleVersion.id;
}
