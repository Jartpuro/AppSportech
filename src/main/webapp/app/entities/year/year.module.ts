import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { YearComponent } from './list/year.component';
import { YearDetailComponent } from './detail/year-detail.component';
import { YearUpdateComponent } from './update/year-update.component';
import { YearDeleteDialogComponent } from './delete/year-delete-dialog.component';
import { YearRoutingModule } from './route/year-routing.module';

@NgModule({
  imports: [SharedModule, YearRoutingModule],
  declarations: [YearComponent, YearDetailComponent, YearUpdateComponent, YearDeleteDialogComponent],
  entryComponents: [YearDeleteDialogComponent],
})
export class YearModule {}
