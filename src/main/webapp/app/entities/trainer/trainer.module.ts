import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TrainerComponent } from './list/trainer.component';
import { TrainerDetailComponent } from './detail/trainer-detail.component';
import { TrainerUpdateComponent } from './update/trainer-update.component';
import { TrainerDeleteDialogComponent } from './delete/trainer-delete-dialog.component';
import { TrainerRoutingModule } from './route/trainer-routing.module';

@NgModule({
  imports: [SharedModule, TrainerRoutingModule],
  declarations: [TrainerComponent, TrainerDetailComponent, TrainerUpdateComponent, TrainerDeleteDialogComponent],
  entryComponents: [TrainerDeleteDialogComponent],
})
export class TrainerModule {}
