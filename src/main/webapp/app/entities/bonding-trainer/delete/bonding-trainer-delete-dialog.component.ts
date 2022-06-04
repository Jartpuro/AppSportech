import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBondingTrainer } from '../bonding-trainer.model';
import { BondingTrainerService } from '../service/bonding-trainer.service';

@Component({
  templateUrl: './bonding-trainer-delete-dialog.component.html',
})
export class BondingTrainerDeleteDialogComponent {
  bondingTrainer?: IBondingTrainer;

  constructor(protected bondingTrainerService: BondingTrainerService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bondingTrainerService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
