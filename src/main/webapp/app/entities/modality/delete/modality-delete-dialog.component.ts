import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IModality } from '../modality.model';
import { ModalityService } from '../service/modality.service';

@Component({
  templateUrl: './modality-delete-dialog.component.html',
})
export class ModalityDeleteDialogComponent {
  modality?: IModality;

  constructor(protected modalityService: ModalityService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.modalityService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
