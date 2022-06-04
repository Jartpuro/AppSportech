import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IScheduleVersion } from '../schedule-version.model';
import { ScheduleVersionService } from '../service/schedule-version.service';

@Component({
  templateUrl: './schedule-version-delete-dialog.component.html',
})
export class ScheduleVersionDeleteDialogComponent {
  scheduleVersion?: IScheduleVersion;

  constructor(protected scheduleVersionService: ScheduleVersionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.scheduleVersionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
