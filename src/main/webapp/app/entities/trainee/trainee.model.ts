import { ICustomer } from 'app/entities/customer/customer.model';
import { ITrainingStatus } from 'app/entities/training-status/training-status.model';
import { ICourse } from 'app/entities/course/course.model';

export interface ITrainee {
  id?: number;
  customer?: ICustomer;
  trainingStatus?: ITrainingStatus;
  course?: ICourse;
}

export class Trainee implements ITrainee {
  constructor(public id?: number, public customer?: ICustomer, public trainingStatus?: ITrainingStatus, public course?: ICourse) {}
}

export function getTraineeIdentifier(trainee: ITrainee): number | undefined {
  return trainee.id;
}
