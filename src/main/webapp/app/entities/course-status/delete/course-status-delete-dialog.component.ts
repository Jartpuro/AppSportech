import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICourseStatus } from '../course-status.model';
import { CourseStatusService } from '../service/course-status.service';

@Component({
  templateUrl: './course-status-delete-dialog.component.html',
})
export class CourseStatusDeleteDialogComponent {
  courseStatus?: ICourseStatus;

  constructor(protected courseStatusService: CourseStatusService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.courseStatusService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
