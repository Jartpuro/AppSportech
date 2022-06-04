import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IModuleSchedule } from '../module-schedule.model';
import { ModuleScheduleService } from '../service/module-schedule.service';

@Component({
  templateUrl: './module-schedule-delete-dialog.component.html',
})
export class ModuleScheduleDeleteDialogComponent {
  moduleSchedule?: IModuleSchedule;

  constructor(protected moduleScheduleService: ModuleScheduleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.moduleScheduleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
