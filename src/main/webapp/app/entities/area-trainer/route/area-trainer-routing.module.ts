import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AreaTrainerComponent } from '../list/area-trainer.component';
import { AreaTrainerDetailComponent } from '../detail/area-trainer-detail.component';
import { AreaTrainerUpdateComponent } from '../update/area-trainer-update.component';
import { AreaTrainerRoutingResolveService } from './area-trainer-routing-resolve.service';

const areaTrainerRoute: Routes = [
  {
    path: '',
    component: AreaTrainerComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AreaTrainerDetailComponent,
    resolve: {
      areaTrainer: AreaTrainerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AreaTrainerUpdateComponent,
    resolve: {
      areaTrainer: AreaTrainerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AreaTrainerUpdateComponent,
    resolve: {
      areaTrainer: AreaTrainerRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(areaTrainerRoute)],
  exports: [RouterModule],
})
export class AreaTrainerRoutingModule {}
