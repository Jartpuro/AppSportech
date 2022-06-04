import { IAreaTrainer } from 'app/entities/area-trainer/area-trainer.model';
import { State } from 'app/entities/enumerations/state.model';

export interface IArea {
  id?: number;
  areaName?: string;
  urlLogo?: string | null;
  areaState?: State;
  areaTrainers?: IAreaTrainer[] | null;
}

export class Area implements IArea {
  constructor(
    public id?: number,
    public areaName?: string,
    public urlLogo?: string | null,
    public areaState?: State,
    public areaTrainers?: IAreaTrainer[] | null
  ) {}
}

export function getAreaIdentifier(area: IArea): number | undefined {
  return area.id;
}
