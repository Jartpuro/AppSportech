import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CourseStatusComponent } from './list/course-status.component';
import { CourseStatusDetailComponent } from './detail/course-status-detail.component';
import { CourseStatusUpdateComponent } from './update/course-status-update.component';
import { CourseStatusDeleteDialogComponent } from './delete/course-status-delete-dialog.component';
import { CourseStatusRoutingModule } from './route/course-status-routing.module';

@NgModule({
  imports: [SharedModule, CourseStatusRoutingModule],
  declarations: [CourseStatusComponent, CourseStatusDetailComponent, CourseStatusUpdateComponent, CourseStatusDeleteDialogComponent],
  entryComponents: [CourseStatusDeleteDialogComponent],
})
export class CourseStatusModule {}
