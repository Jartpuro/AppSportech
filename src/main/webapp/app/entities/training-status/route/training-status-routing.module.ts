import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TrainingStatusComponent } from '../list/training-status.component';
import { TrainingStatusDetailComponent } from '../detail/training-status-detail.component';
import { TrainingStatusUpdateComponent } from '../update/training-status-update.component';
import { TrainingStatusRoutingResolveService } from './training-status-routing-resolve.service';

const trainingStatusRoute: Routes = [
  {
    path: '',
    component: TrainingStatusComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrainingStatusDetailComponent,
    resolve: {
      trainingStatus: TrainingStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrainingStatusUpdateComponent,
    resolve: {
      trainingStatus: TrainingStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrainingStatusUpdateComponent,
    resolve: {
      trainingStatus: TrainingStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(trainingStatusRoute)],
  exports: [RouterModule],
})
export class TrainingStatusRoutingModule {}
