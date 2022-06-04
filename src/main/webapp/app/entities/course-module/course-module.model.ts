import { ISchedule } from 'app/entities/schedule/schedule.model';
import { ICourse } from 'app/entities/course/course.model';
import { IModule } from 'app/entities/module/module.model';

export interface ICourseModule {
  id?: number;
  schedules?: ISchedule[] | null;
  course?: ICourse;
  module?: IModule;
}

export class CourseModule implements ICourseModule {
  constructor(public id?: number, public schedules?: ISchedule[] | null, public course?: ICourse, public module?: IModule) {}
}

export function getCourseModuleIdentifier(courseModule: ICourseModule): number | undefined {
  return courseModule.id;
}
