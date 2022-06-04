import { IBondingTrainer } from 'app/entities/bonding-trainer/bonding-trainer.model';
import { State } from 'app/entities/enumerations/state.model';

export interface IBonding {
  id?: number;
  bondingType?: string;
  workingHours?: number;
  bondingState?: State;
  bondingTrainers?: IBondingTrainer[] | null;
}

export class Bonding implements IBonding {
  constructor(
    public id?: number,
    public bondingType?: string,
    public workingHours?: number,
    public bondingState?: State,
    public bondingTrainers?: IBondingTrainer[] | null
  ) {}
}

export function getBondingIdentifier(bonding: IBonding): number | undefined {
  return bonding.id;
}
