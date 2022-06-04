import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TraineeComponent } from './list/trainee.component';
import { TraineeDetailComponent } from './detail/trainee-detail.component';
import { TraineeUpdateComponent } from './update/trainee-update.component';
import { TraineeDeleteDialogComponent } from './delete/trainee-delete-dialog.component';
import { TraineeRoutingModule } from './route/trainee-routing.module';

@NgModule({
  imports: [SharedModule, TraineeRoutingModule],
  declarations: [TraineeComponent, TraineeDetailComponent, TraineeUpdateComponent, TraineeDeleteDialogComponent],
  entryComponents: [TraineeDeleteDialogComponent],
})
export class TraineeModule {}
