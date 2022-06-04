import { ICourse } from 'app/entities/course/course.model';
import { State } from 'app/entities/enumerations/state.model';

export interface ICourseStatus {
  id?: number;
  nameCourseStatus?: string;
  stateCourse?: State;
  courses?: ICourse[] | null;
}

export class CourseStatus implements ICourseStatus {
  constructor(public id?: number, public nameCourseStatus?: string, public stateCourse?: State, public courses?: ICourse[] | null) {}
}

export function getCourseStatusIdentifier(courseStatus: ICourseStatus): number | undefined {
  return courseStatus.id;
}
