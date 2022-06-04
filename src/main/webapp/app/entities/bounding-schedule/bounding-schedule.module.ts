import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BoundingScheduleComponent } from './list/bounding-schedule.component';
import { BoundingScheduleDetailComponent } from './detail/bounding-schedule-detail.component';
import { BoundingScheduleUpdateComponent } from './update/bounding-schedule-update.component';
import { BoundingScheduleDeleteDialogComponent } from './delete/bounding-schedule-delete-dialog.component';
import { BoundingScheduleRoutingModule } from './route/bounding-schedule-routing.module';

@NgModule({
  imports: [SharedModule, BoundingScheduleRoutingModule],
  declarations: [
    BoundingScheduleComponent,
    BoundingScheduleDetailComponent,
    BoundingScheduleUpdateComponent,
    BoundingScheduleDeleteDialogComponent,
  ],
  entryComponents: [BoundingScheduleDeleteDialogComponent],
})
export class BoundingScheduleModule {}
