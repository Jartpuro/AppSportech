import { IAreaTrainer } from 'app/entities/area-trainer/area-trainer.model';
import { IBondingTrainer } from 'app/entities/bonding-trainer/bonding-trainer.model';
import { ISchedule } from 'app/entities/schedule/schedule.model';
import { ICustomer } from 'app/entities/customer/customer.model';
import { State } from 'app/entities/enumerations/state.model';

export interface ITrainer {
  id?: number;
  trainerState?: State;
  areaTrainers?: IAreaTrainer[] | null;
  bondingTrainers?: IBondingTrainer[] | null;
  schedules?: ISchedule[] | null;
  customer?: ICustomer;
}

export class Trainer implements ITrainer {
  constructor(
    public id?: number,
    public trainerState?: State,
    public areaTrainers?: IAreaTrainer[] | null,
    public bondingTrainers?: IBondingTrainer[] | null,
    public schedules?: ISchedule[] | null,
    public customer?: ICustomer
  ) {}
}

export function getTrainerIdentifier(trainer: ITrainer): number | undefined {
  return trainer.id;
}
