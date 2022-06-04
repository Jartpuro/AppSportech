import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ClassroomTypeComponent } from './list/classroom-type.component';
import { ClassroomTypeDetailComponent } from './detail/classroom-type-detail.component';
import { ClassroomTypeUpdateComponent } from './update/classroom-type-update.component';
import { ClassroomTypeDeleteDialogComponent } from './delete/classroom-type-delete-dialog.component';
import { ClassroomTypeRoutingModule } from './route/classroom-type-routing.module';

@NgModule({
  imports: [SharedModule, ClassroomTypeRoutingModule],
  declarations: [ClassroomTypeComponent, ClassroomTypeDetailComponent, ClassroomTypeUpdateComponent, ClassroomTypeDeleteDialogComponent],
  entryComponents: [ClassroomTypeDeleteDialogComponent],
})
export class ClassroomTypeModule {}
