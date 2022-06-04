import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BondingComponent } from './list/bonding.component';
import { BondingDetailComponent } from './detail/bonding-detail.component';
import { BondingUpdateComponent } from './update/bonding-update.component';
import { BondingDeleteDialogComponent } from './delete/bonding-delete-dialog.component';
import { BondingRoutingModule } from './route/bonding-routing.module';

@NgModule({
  imports: [SharedModule, BondingRoutingModule],
  declarations: [BondingComponent, BondingDetailComponent, BondingUpdateComponent, BondingDeleteDialogComponent],
  entryComponents: [BondingDeleteDialogComponent],
})
export class BondingModule {}
