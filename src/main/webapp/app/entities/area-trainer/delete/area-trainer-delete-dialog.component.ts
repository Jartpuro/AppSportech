import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAreaTrainer } from '../area-trainer.model';
import { AreaTrainerService } from '../service/area-trainer.service';

@Component({
  templateUrl: './area-trainer-delete-dialog.component.html',
})
export class AreaTrainerDeleteDialogComponent {
  areaTrainer?: IAreaTrainer;

  constructor(protected areaTrainerService: AreaTrainerService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.areaTrainerService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
