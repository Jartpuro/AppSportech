import { ICourseModule } from 'app/entities/course-module/course-module.model';
import { IModuleSchedule } from 'app/entities/module-schedule/module-schedule.model';

export interface IModule {
  id?: number;
  moduleName?: number;
  moduleState?: string | null;
  courseModules?: ICourseModule[] | null;
  moduleSchedules?: IModuleSchedule[] | null;
}

export class Module implements IModule {
  constructor(
    public id?: number,
    public moduleName?: number,
    public moduleState?: string | null,
    public courseModules?: ICourseModule[] | null,
    public moduleSchedules?: IModuleSchedule[] | null
  ) {}
}

export function getModuleIdentifier(module: IModule): number | undefined {
  return module.id;
}
