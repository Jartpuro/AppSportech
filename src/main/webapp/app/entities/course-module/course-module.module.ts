import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CourseModuleComponent } from './list/course-module.component';
import { CourseModuleDetailComponent } from './detail/course-module-detail.component';
import { CourseModuleUpdateComponent } from './update/course-module-update.component';
import { CourseModuleDeleteDialogComponent } from './delete/course-module-delete-dialog.component';
import { CourseModuleRoutingModule } from './route/course-module-routing.module';

@NgModule({
  imports: [SharedModule, CourseModuleRoutingModule],
  declarations: [CourseModuleComponent, CourseModuleDetailComponent, CourseModuleUpdateComponent, CourseModuleDeleteDialogComponent],
  entryComponents: [CourseModuleDeleteDialogComponent],
})
export class CourseModuleModule {}
