import { ICourse } from 'app/entities/course/course.model';
import { Offer } from 'app/entities/enumerations/offer.model';
import { StateProgram } from 'app/entities/enumerations/state-program.model';

export interface ITrainingProgram {
  id?: number;
  programCode?: string;
  programVersion?: string;
  programName?: Offer;
  programInitials?: string;
  programState?: StateProgram;
  courses?: ICourse[] | null;
}

export class TrainingProgram implements ITrainingProgram {
  constructor(
    public id?: number,
    public programCode?: string,
    public programVersion?: string,
    public programName?: Offer,
    public programInitials?: string,
    public programState?: StateProgram,
    public courses?: ICourse[] | null
  ) {}
}

export function getTrainingProgramIdentifier(trainingProgram: ITrainingProgram): number | undefined {
  return trainingProgram.id;
}
