import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ModalityComponent } from './list/modality.component';
import { ModalityDetailComponent } from './detail/modality-detail.component';
import { ModalityUpdateComponent } from './update/modality-update.component';
import { ModalityDeleteDialogComponent } from './delete/modality-delete-dialog.component';
import { ModalityRoutingModule } from './route/modality-routing.module';

@NgModule({
  imports: [SharedModule, ModalityRoutingModule],
  declarations: [ModalityComponent, ModalityDetailComponent, ModalityUpdateComponent, ModalityDeleteDialogComponent],
  entryComponents: [ModalityDeleteDialogComponent],
})
export class ModalityModule {}
