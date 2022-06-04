import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILogAudit } from '../log-audit.model';
import { LogAuditService } from '../service/log-audit.service';

@Component({
  templateUrl: './log-audit-delete-dialog.component.html',
})
export class LogAuditDeleteDialogComponent {
  logAudit?: ILogAudit;

  constructor(protected logAuditService: LogAuditService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.logAuditService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
