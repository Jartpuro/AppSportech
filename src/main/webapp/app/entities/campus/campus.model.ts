import { IClassroom } from 'app/entities/classroom/classroom.model';
import { State } from 'app/entities/enumerations/state.model';

export interface ICampus {
  id?: number;
  campusName?: string;
  campusAddress?: string;
  campusState?: State;
  classrooms?: IClassroom[] | null;
}

export class Campus implements ICampus {
  constructor(
    public id?: number,
    public campusName?: string,
    public campusAddress?: string,
    public campusState?: State,
    public classrooms?: IClassroom[] | null
  ) {}
}

export function getCampusIdentifier(campus: ICampus): number | undefined {
  return campus.id;
}
