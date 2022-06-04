import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrainee } from '../trainee.model';
import { TraineeService } from '../service/trainee.service';

@Component({
  templateUrl: './trainee-delete-dialog.component.html',
})
export class TraineeDeleteDialogComponent {
  trainee?: ITrainee;

  constructor(protected traineeService: TraineeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.traineeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
