import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBoundingSchedule } from '../bounding-schedule.model';
import { BoundingScheduleService } from '../service/bounding-schedule.service';

@Component({
  templateUrl: './bounding-schedule-delete-dialog.component.html',
})
export class BoundingScheduleDeleteDialogComponent {
  boundingSchedule?: IBoundingSchedule;

  constructor(protected boundingScheduleService: BoundingScheduleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.boundingScheduleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
