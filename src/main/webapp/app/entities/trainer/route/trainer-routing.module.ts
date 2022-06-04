import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TrainerComponent } from '../list/trainer.component';
import { TrainerDetailComponent } from '../detail/trainer-detail.component';
import { TrainerUpdateComponent } from '../update/trainer-update.component';
import { TrainerRoutingResolveService } from './trainer-routing-resolve.service';

const trainerRoute: Routes = [
  {
    path: '',
    component: TrainerComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrainerDetailComponent,
    resolve: {
      trainer: TrainerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrainerUpdateComponent,
    resolve: {
      trainer: TrainerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrainerUpdateComponent,
    resolve: {
      trainer: TrainerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(trainerRoute)],
  exports: [RouterModule],
})
export class TrainerRoutingModule {}
