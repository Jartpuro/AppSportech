import { IArea } from 'app/entities/area/area.model';
import { ITrainer } from 'app/entities/trainer/trainer.model';
import { State } from 'app/entities/enumerations/state.model';

export interface IAreaTrainer {
  id?: number;
  areaTrainerState?: State;
  area?: IArea;
  trainer?: ITrainer;
}

export class AreaTrainer implements IAreaTrainer {
  constructor(public id?: number, public areaTrainerState?: State, public area?: IArea, public trainer?: ITrainer) {}
}

export function getAreaTrainerIdentifier(areaTrainer: IAreaTrainer): number | undefined {
  return areaTrainer.id;
}
