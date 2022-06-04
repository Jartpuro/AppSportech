import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TrainingStatusComponent } from './list/training-status.component';
import { TrainingStatusDetailComponent } from './detail/training-status-detail.component';
import { TrainingStatusUpdateComponent } from './update/training-status-update.component';
import { TrainingStatusDeleteDialogComponent } from './delete/training-status-delete-dialog.component';
import { TrainingStatusRoutingModule } from './route/training-status-routing.module';

@NgModule({
  imports: [SharedModule, TrainingStatusRoutingModule],
  declarations: [
    TrainingStatusComponent,
    TrainingStatusDetailComponent,
    TrainingStatusUpdateComponent,
    TrainingStatusDeleteDialogComponent,
  ],
  entryComponents: [TrainingStatusDeleteDialogComponent],
})
export class TrainingStatusModule {}
