import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BondingTrainerComponent } from './list/bonding-trainer.component';
import { BondingTrainerDetailComponent } from './detail/bonding-trainer-detail.component';
import { BondingTrainerUpdateComponent } from './update/bonding-trainer-update.component';
import { BondingTrainerDeleteDialogComponent } from './delete/bonding-trainer-delete-dialog.component';
import { BondingTrainerRoutingModule } from './route/bonding-trainer-routing.module';

@NgModule({
  imports: [SharedModule, BondingTrainerRoutingModule],
  declarations: [
    BondingTrainerComponent,
    BondingTrainerDetailComponent,
    BondingTrainerUpdateComponent,
    BondingTrainerDeleteDialogComponent,
  ],
  entryComponents: [BondingTrainerDeleteDialogComponent],
})
export class BondingTrainerModule {}
