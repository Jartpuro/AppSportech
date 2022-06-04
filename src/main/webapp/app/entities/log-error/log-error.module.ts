import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LogErrorComponent } from './list/log-error.component';
import { LogErrorDetailComponent } from './detail/log-error-detail.component';
import { LogErrorUpdateComponent } from './update/log-error-update.component';
import { LogErrorDeleteDialogComponent } from './delete/log-error-delete-dialog.component';
import { LogErrorRoutingModule } from './route/log-error-routing.module';

@NgModule({
  imports: [SharedModule, LogErrorRoutingModule],
  declarations: [LogErrorComponent, LogErrorDetailComponent, LogErrorUpdateComponent, LogErrorDeleteDialogComponent],
  entryComponents: [LogErrorDeleteDialogComponent],
})
export class LogErrorModule {}
