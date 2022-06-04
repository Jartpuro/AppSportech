import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IYear } from '../year.model';
import { YearService } from '../service/year.service';

@Component({
  templateUrl: './year-delete-dialog.component.html',
})
export class YearDeleteDialogComponent {
  year?: IYear;

  constructor(protected yearService: YearService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.yearService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
