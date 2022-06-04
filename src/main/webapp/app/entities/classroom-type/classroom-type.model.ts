import { IClassroom } from 'app/entities/classroom/classroom.model';
import { State } from 'app/entities/enumerations/state.model';

export interface IClassroomType {
  id?: number;
  typeClassroom?: string;
  classroomDescription?: string;
  classroomState?: State;
  classrooms?: IClassroom[] | null;
}

export class ClassroomType implements IClassroomType {
  constructor(
    public id?: number,
    public typeClassroom?: string,
    public classroomDescription?: string,
    public classroomState?: State,
    public classrooms?: IClassroom[] | null
  ) {}
}

export function getClassroomTypeIdentifier(classroomType: IClassroomType): number | undefined {
  return classroomType.id;
}
