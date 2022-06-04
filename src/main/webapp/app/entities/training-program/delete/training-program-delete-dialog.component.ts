import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrainingProgram } from '../training-program.model';
import { TrainingProgramService } from '../service/training-program.service';

@Component({
  templateUrl: './training-program-delete-dialog.component.html',
})
export class TrainingProgramDeleteDialogComponent {
  trainingProgram?: ITrainingProgram;

  constructor(protected trainingProgramService: TrainingProgramService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trainingProgramService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
