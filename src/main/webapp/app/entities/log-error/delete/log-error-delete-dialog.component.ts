import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILogError } from '../log-error.model';
import { LogErrorService } from '../service/log-error.service';

@Component({
  templateUrl: './log-error-delete-dialog.component.html',
})
export class LogErrorDeleteDialogComponent {
  logError?: ILogError;

  constructor(protected logErrorService: LogErrorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.logErrorService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
