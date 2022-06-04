import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AreaTrainerComponent } from './list/area-trainer.component';
import { AreaTrainerDetailComponent } from './detail/area-trainer-detail.component';
import { AreaTrainerUpdateComponent } from './update/area-trainer-update.component';
import { AreaTrainerDeleteDialogComponent } from './delete/area-trainer-delete-dialog.component';
import { AreaTrainerRoutingModule } from './route/area-trainer-routing.module';

@NgModule({
  imports: [SharedModule, AreaTrainerRoutingModule],
  declarations: [AreaTrainerComponent, AreaTrainerDetailComponent, AreaTrainerUpdateComponent, AreaTrainerDeleteDialogComponent],
  entryComponents: [AreaTrainerDeleteDialogComponent],
})
export class AreaTrainerModule {}
