import dayjs from 'dayjs/esm';
import { IBoundingSchedule } from 'app/entities/bounding-schedule/bounding-schedule.model';
import { IYear } from 'app/entities/year/year.model';
import { ITrainer } from 'app/entities/trainer/trainer.model';
import { IBonding } from 'app/entities/bonding/bonding.model';

export interface IBondingTrainer {
  id?: number;
  startTime?: dayjs.Dayjs;
  endTime?: dayjs.Dayjs;
  boundingSchedules?: IBoundingSchedule[] | null;
  year?: IYear;
  trainer?: ITrainer;
  bonding?: IBonding;
}

export class BondingTrainer implements IBondingTrainer {
  constructor(
    public id?: number,
    public startTime?: dayjs.Dayjs,
    public endTime?: dayjs.Dayjs,
    public boundingSchedules?: IBoundingSchedule[] | null,
    public year?: IYear,
    public trainer?: ITrainer,
    public bonding?: IBonding
  ) {}
}

export function getBondingTrainerIdentifier(bondingTrainer: IBondingTrainer): number | undefined {
  return bondingTrainer.id;
}
