import { ISchedule } from 'app/entities/schedule/schedule.model';
import { IClassroomType } from 'app/entities/classroom-type/classroom-type.model';
import { ICampus } from 'app/entities/campus/campus.model';

export interface IClassroom {
  id?: number;
  classroomNumber?: string;
  classroomDescription?: string;
  classroomState?: string;
  schedules?: ISchedule[] | null;
  classroomType?: IClassroomType;
  campus?: ICampus;
}

export class Classroom implements IClassroom {
  constructor(
    public id?: number,
    public classroomNumber?: string,
    public classroomDescription?: string,
    public classroomState?: string,
    public schedules?: ISchedule[] | null,
    public classroomType?: IClassroomType,
    public campus?: ICampus
  ) {}
}

export function getClassroomIdentifier(classroom: IClassroom): number | undefined {
  return classroom.id;
}
