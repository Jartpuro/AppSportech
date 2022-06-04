import { IBondingTrainer } from 'app/entities/bonding-trainer/bonding-trainer.model';
import { State } from 'app/entities/enumerations/state.model';

export interface IYear {
  id?: number;
  yearNumber?: number;
  yearState?: State;
  bondingTrainers?: IBondingTrainer[] | null;
}

export class Year implements IYear {
  constructor(
    public id?: number,
    public yearNumber?: number,
    public yearState?: State,
    public bondingTrainers?: IBondingTrainer[] | null
  ) {}
}

export function getYearIdentifier(year: IYear): number | undefined {
  return year.id;
}
