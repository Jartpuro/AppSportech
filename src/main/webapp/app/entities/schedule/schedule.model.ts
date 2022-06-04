import { IScheduleVersion } from 'app/entities/schedule-version/schedule-version.model';
import { IModality } from 'app/entities/modality/modality.model';
import { IDay } from 'app/entities/day/day.model';
import { ICourseModule } from 'app/entities/course-module/course-module.model';
import { IClassroom } from 'app/entities/classroom/classroom.model';
import { ITrainer } from 'app/entities/trainer/trainer.model';

export interface ISchedule {
  id?: number;
  startTime?: string;
  endTime?: string;
  scheduleVersion?: IScheduleVersion;
  modality?: IModality;
  day?: IDay;
  courseModule?: ICourseModule;
  classroom?: IClassroom;
  trainer?: ITrainer;
}

export class Schedule implements ISchedule {
  constructor(
    public id?: number,
    public startTime?: string,
    public endTime?: string,
    public scheduleVersion?: IScheduleVersion,
    public modality?: IModality,
    public day?: IDay,
    public courseModule?: ICourseModule,
    public classroom?: IClassroom,
    public trainer?: ITrainer
  ) {}
}

export function getScheduleIdentifier(schedule: ISchedule): number | undefined {
  return schedule.id;
}
