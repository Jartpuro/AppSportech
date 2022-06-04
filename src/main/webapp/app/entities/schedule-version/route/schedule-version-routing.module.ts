import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ScheduleVersionComponent } from '../list/schedule-version.component';
import { ScheduleVersionDetailComponent } from '../detail/schedule-version-detail.component';
import { ScheduleVersionUpdateComponent } from '../update/schedule-version-update.component';
import { ScheduleVersionRoutingResolveService } from './schedule-version-routing-resolve.service';

const scheduleVersionRoute: Routes = [
  {
    path: '',
    component: ScheduleVersionComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ScheduleVersionDetailComponent,
    resolve: {
      scheduleVersion: ScheduleVersionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ScheduleVersionUpdateComponent,
    resolve: {
      scheduleVersion: ScheduleVersionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ScheduleVersionUpdateComponent,
    resolve: {
      scheduleVersion: ScheduleVersionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(scheduleVersionRoute)],
  exports: [RouterModule],
})
export class ScheduleVersionRoutingModule {}
