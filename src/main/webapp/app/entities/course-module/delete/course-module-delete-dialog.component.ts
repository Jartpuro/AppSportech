import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICourseModule } from '../course-module.model';
import { CourseModuleService } from '../service/course-module.service';

@Component({
  templateUrl: './course-module-delete-dialog.component.html',
})
export class CourseModuleDeleteDialogComponent {
  courseModule?: ICourseModule;

  constructor(protected courseModuleService: CourseModuleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.courseModuleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
