import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBonding } from '../bonding.model';
import { BondingService } from '../service/bonding.service';

@Component({
  templateUrl: './bonding-delete-dialog.component.html',
})
export class BondingDeleteDialogComponent {
  bonding?: IBonding;

  constructor(protected bondingService: BondingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bondingService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
