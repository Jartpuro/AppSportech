import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BondingTrainerComponent } from '../list/bonding-trainer.component';
import { BondingTrainerDetailComponent } from '../detail/bonding-trainer-detail.component';
import { BondingTrainerUpdateComponent } from '../update/bonding-trainer-update.component';
import { BondingTrainerRoutingResolveService } from './bonding-trainer-routing-resolve.service';

const bondingTrainerRoute: Routes = [
  {
    path: '',
    component: BondingTrainerComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BondingTrainerDetailComponent,
    resolve: {
      bondingTrainer: BondingTrainerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BondingTrainerUpdateComponent,
    resolve: {
      bondingTrainer: BondingTrainerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BondingTrainerUpdateComponent,
    resolve: {
      bondingTrainer: BondingTrainerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bondingTrainerRoute)],
  exports: [RouterModule],
})
export class BondingTrainerRoutingModule {}
