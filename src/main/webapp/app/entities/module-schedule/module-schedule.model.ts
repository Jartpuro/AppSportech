import { IModule } from 'app/entities/module/module.model';

export interface IModuleSchedule {
  id?: number;
  module?: IModule;
}

export class ModuleSchedule implements IModuleSchedule {
  constructor(public id?: number, public module?: IModule) {}
}

export function getModuleScheduleIdentifier(moduleSchedule: IModuleSchedule): number | undefined {
  return moduleSchedule.id;
}
