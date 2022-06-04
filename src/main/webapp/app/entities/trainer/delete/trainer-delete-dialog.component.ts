import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrainer } from '../trainer.model';
import { TrainerService } from '../service/trainer.service';

@Component({
  templateUrl: './trainer-delete-dialog.component.html',
})
export class TrainerDeleteDialogComponent {
  trainer?: ITrainer;

  constructor(protected trainerService: TrainerService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trainerService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
