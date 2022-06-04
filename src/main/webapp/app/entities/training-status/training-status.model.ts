import { ITrainee } from 'app/entities/trainee/trainee.model';
import { State } from 'app/entities/enumerations/state.model';

export interface ITrainingStatus {
  id?: number;
  statusName?: string;
  stateTraining?: State;
  trainees?: ITrainee[] | null;
}

export class TrainingStatus implements ITrainingStatus {
  constructor(public id?: number, public statusName?: string, public stateTraining?: State, public trainees?: ITrainee[] | null) {}
}

export function getTrainingStatusIdentifier(trainingStatus: ITrainingStatus): number | undefined {
  return trainingStatus.id;
}
