import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDay } from '../day.model';
import { DayService } from '../service/day.service';

@Component({
  templateUrl: './day-delete-dialog.component.html',
})
export class DayDeleteDialogComponent {
  day?: IDay;

  constructor(protected dayService: DayService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dayService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
