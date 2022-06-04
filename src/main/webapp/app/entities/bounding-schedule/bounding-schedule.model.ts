import { IBondingTrainer } from 'app/entities/bonding-trainer/bonding-trainer.model';

export interface IBoundingSchedule {
  id?: number;
  bondingTrainer?: IBondingTrainer;
}

export class BoundingSchedule implements IBoundingSchedule {
  constructor(public id?: number, public bondingTrainer?: IBondingTrainer) {}
}

export function getBoundingScheduleIdentifier(boundingSchedule: IBoundingSchedule): number | undefined {
  return boundingSchedule.id;
}
