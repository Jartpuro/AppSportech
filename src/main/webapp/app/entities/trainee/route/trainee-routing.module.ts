import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TraineeComponent } from '../list/trainee.component';
import { TraineeDetailComponent } from '../detail/trainee-detail.component';
import { TraineeUpdateComponent } from '../update/trainee-update.component';
import { TraineeRoutingResolveService } from './trainee-routing-resolve.service';

const traineeRoute: Routes = [
  {
    path: '',
    component: TraineeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TraineeDetailComponent,
    resolve: {
      trainee: TraineeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TraineeUpdateComponent,
    resolve: {
      trainee: TraineeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TraineeUpdateComponent,
    resolve: {
      trainee: TraineeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(traineeRoute)],
  exports: [RouterModule],
})
export class TraineeRoutingModule {}
