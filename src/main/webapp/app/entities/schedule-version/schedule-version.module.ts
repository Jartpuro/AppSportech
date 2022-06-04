import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ScheduleVersionComponent } from './list/schedule-version.component';
import { ScheduleVersionDetailComponent } from './detail/schedule-version-detail.component';
import { ScheduleVersionUpdateComponent } from './update/schedule-version-update.component';
import { ScheduleVersionDeleteDialogComponent } from './delete/schedule-version-delete-dialog.component';
import { ScheduleVersionRoutingModule } from './route/schedule-version-routing.module';

@NgModule({
  imports: [SharedModule, ScheduleVersionRoutingModule],
  declarations: [
    ScheduleVersionComponent,
    ScheduleVersionDetailComponent,
    ScheduleVersionUpdateComponent,
    ScheduleVersionDeleteDialogComponent,
  ],
  entryComponents: [ScheduleVersionDeleteDialogComponent],
})
export class ScheduleVersionModule {}
