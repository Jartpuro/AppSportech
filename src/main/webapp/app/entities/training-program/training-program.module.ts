import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TrainingProgramComponent } from './list/training-program.component';
import { TrainingProgramDetailComponent } from './detail/training-program-detail.component';
import { TrainingProgramUpdateComponent } from './update/training-program-update.component';
import { TrainingProgramDeleteDialogComponent } from './delete/training-program-delete-dialog.component';
import { TrainingProgramRoutingModule } from './route/training-program-routing.module';

@NgModule({
  imports: [SharedModule, TrainingProgramRoutingModule],
  declarations: [
    TrainingProgramComponent,
    TrainingProgramDetailComponent,
    TrainingProgramUpdateComponent,
    TrainingProgramDeleteDialogComponent,
  ],
  entryComponents: [TrainingProgramDeleteDialogComponent],
})
export class TrainingProgramModule {}
