import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TrainingProgramComponent } from '../list/training-program.component';
import { TrainingProgramDetailComponent } from '../detail/training-program-detail.component';
import { TrainingProgramUpdateComponent } from '../update/training-program-update.component';
import { TrainingProgramRoutingResolveService } from './training-program-routing-resolve.service';

const trainingProgramRoute: Routes = [
  {
    path: '',
    component: TrainingProgramComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrainingProgramDetailComponent,
    resolve: {
      trainingProgram: TrainingProgramRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrainingProgramUpdateComponent,
    resolve: {
      trainingProgram: TrainingProgramRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrainingProgramUpdateComponent,
    resolve: {
      trainingProgram: TrainingProgramRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(trainingProgramRoute)],
  exports: [RouterModule],
})
export class TrainingProgramRoutingModule {}
