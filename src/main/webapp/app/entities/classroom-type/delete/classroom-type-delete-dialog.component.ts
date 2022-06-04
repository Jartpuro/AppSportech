import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IClassroomType } from '../classroom-type.model';
import { ClassroomTypeService } from '../service/classroom-type.service';

@Component({
  templateUrl: './classroom-type-delete-dialog.component.html',
})
export class ClassroomTypeDeleteDialogComponent {
  classroomType?: IClassroomType;

  constructor(protected classroomTypeService: ClassroomTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.classroomTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
