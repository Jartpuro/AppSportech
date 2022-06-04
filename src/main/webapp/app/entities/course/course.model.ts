import dayjs from 'dayjs/esm';
import { ITrainee } from 'app/entities/trainee/trainee.model';
import { ICourseModule } from 'app/entities/course-module/course-module.model';
import { ICourseStatus } from 'app/entities/course-status/course-status.model';
import { ITrainingProgram } from 'app/entities/training-program/training-program.model';

export interface ICourse {
  id?: number;
  courseNumber?: string;
  startDate?: dayjs.Dayjs;
  endDate?: dayjs.Dayjs;
  route?: string;
  trainees?: ITrainee[] | null;
  courseModules?: ICourseModule[] | null;
  courseStatus?: ICourseStatus;
  trainingProgram?: ITrainingProgram;
}

export class Course implements ICourse {
  constructor(
    public id?: number,
    public courseNumber?: string,
    public startDate?: dayjs.Dayjs,
    public endDate?: dayjs.Dayjs,
    public route?: string,
    public trainees?: ITrainee[] | null,
    public courseModules?: ICourseModule[] | null,
    public courseStatus?: ICourseStatus,
    public trainingProgram?: ITrainingProgram
  ) {}
}

export function getCourseIdentifier(course: ICourse): number | undefined {
  return course.id;
}
