import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LogAuditComponent } from './list/log-audit.component';
import { LogAuditDetailComponent } from './detail/log-audit-detail.component';
import { LogAuditUpdateComponent } from './update/log-audit-update.component';
import { LogAuditDeleteDialogComponent } from './delete/log-audit-delete-dialog.component';
import { LogAuditRoutingModule } from './route/log-audit-routing.module';

@NgModule({
  imports: [SharedModule, LogAuditRoutingModule],
  declarations: [LogAuditComponent, LogAuditDetailComponent, LogAuditUpdateComponent, LogAuditDeleteDialogComponent],
  entryComponents: [LogAuditDeleteDialogComponent],
})
export class LogAuditModule {}
