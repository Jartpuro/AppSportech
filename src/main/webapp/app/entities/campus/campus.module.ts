import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CampusComponent } from './list/campus.component';
import { CampusDetailComponent } from './detail/campus-detail.component';
import { CampusUpdateComponent } from './update/campus-update.component';
import { CampusDeleteDialogComponent } from './delete/campus-delete-dialog.component';
import { CampusRoutingModule } from './route/campus-routing.module';

@NgModule({
  imports: [SharedModule, CampusRoutingModule],
  declarations: [CampusComponent, CampusDetailComponent, CampusUpdateComponent, CampusDeleteDialogComponent],
  entryComponents: [CampusDeleteDialogComponent],
})
export class CampusModule {}
