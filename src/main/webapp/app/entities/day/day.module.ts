import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DayComponent } from './list/day.component';
import { DayDetailComponent } from './detail/day-detail.component';
import { DayUpdateComponent } from './update/day-update.component';
import { DayDeleteDialogComponent } from './delete/day-delete-dialog.component';
import { DayRoutingModule } from './route/day-routing.module';

@NgModule({
  imports: [SharedModule, DayRoutingModule],
  declarations: [DayComponent, DayDetailComponent, DayUpdateComponent, DayDeleteDialogComponent],
  entryComponents: [DayDeleteDialogComponent],
})
export class DayModule {}
