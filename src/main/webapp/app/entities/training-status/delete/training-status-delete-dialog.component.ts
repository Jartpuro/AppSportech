import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrainingStatus } from '../training-status.model';
import { TrainingStatusService } from '../service/training-status.service';

@Component({
  templateUrl: './training-status-delete-dialog.component.html',
})
export class TrainingStatusDeleteDialogComponent {
  trainingStatus?: ITrainingStatus;

  constructor(protected trainingStatusService: TrainingStatusService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trainingStatusService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
