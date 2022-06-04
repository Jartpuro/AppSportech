import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BondingComponent } from '../list/bonding.component';
import { BondingDetailComponent } from '../detail/bonding-detail.component';
import { BondingUpdateComponent } from '../update/bonding-update.component';
import { BondingRoutingResolveService } from './bonding-routing-resolve.service';

const bondingRoute: Routes = [
  {
    path: '',
    component: BondingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BondingDetailComponent,
    resolve: {
      bonding: BondingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BondingUpdateComponent,
    resolve: {
      bonding: BondingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BondingUpdateComponent,
    resolve: {
      bonding: BondingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bondingRoute)],
  exports: [RouterModule],
})
export class BondingRoutingModule {}
