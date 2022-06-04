import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BoundingScheduleComponent } from '../list/bounding-schedule.component';
import { BoundingScheduleDetailComponent } from '../detail/bounding-schedule-detail.component';
import { BoundingScheduleUpdateComponent } from '../update/bounding-schedule-update.component';
import { BoundingScheduleRoutingResolveService } from './bounding-schedule-routing-resolve.service';

const boundingScheduleRoute: Routes = [
  {
    path: '',
    component: BoundingScheduleComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BoundingScheduleDetailComponent,
    resolve: {
      boundingSchedule: BoundingScheduleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BoundingScheduleUpdateComponent,
    resolve: {
      boundingSchedule: BoundingScheduleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BoundingScheduleUpdateComponent,
    resolve: {
      boundingSchedule: BoundingScheduleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(boundingScheduleRoute)],
  exports: [RouterModule],
})
export class BoundingScheduleRoutingModule {}
