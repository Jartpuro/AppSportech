import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICampus } from '../campus.model';
import { CampusService } from '../service/campus.service';

@Component({
  templateUrl: './campus-delete-dialog.component.html',
})
export class CampusDeleteDialogComponent {
  campus?: ICampus;

  constructor(protected campusService: CampusService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.campusService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
