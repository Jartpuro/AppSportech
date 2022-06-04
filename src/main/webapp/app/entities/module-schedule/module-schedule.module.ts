import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ModuleScheduleComponent } from './list/module-schedule.component';
import { ModuleScheduleDetailComponent } from './detail/module-schedule-detail.component';
import { ModuleScheduleUpdateComponent } from './update/module-schedule-update.component';
import { ModuleScheduleDeleteDialogComponent } from './delete/module-schedule-delete-dialog.component';
import { ModuleScheduleRoutingModule } from './route/module-schedule-routing.module';

@NgModule({
  imports: [SharedModule, ModuleScheduleRoutingModule],
  declarations: [
    ModuleScheduleComponent,
    ModuleScheduleDetailComponent,
    ModuleScheduleUpdateComponent,
    ModuleScheduleDeleteDialogComponent,
  ],
  entryComponents: [ModuleScheduleDeleteDialogComponent],
})
export class ModuleScheduleModule {}
