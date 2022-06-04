import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ModuleScheduleComponent } from '../list/module-schedule.component';
import { ModuleScheduleDetailComponent } from '../detail/module-schedule-detail.component';
import { ModuleScheduleUpdateComponent } from '../update/module-schedule-update.component';
import { ModuleScheduleRoutingResolveService } from './module-schedule-routing-resolve.service';

const moduleScheduleRoute: Routes = [
  {
    path: '',
    component: ModuleScheduleComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ModuleScheduleDetailComponent,
    resolve: {
      moduleSchedule: ModuleScheduleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ModuleScheduleUpdateComponent,
    resolve: {
      moduleSchedule: ModuleScheduleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ModuleScheduleUpdateComponent,
    resolve: {
      moduleSchedule: ModuleScheduleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(moduleScheduleRoute)],
  exports: [RouterModule],
})
export class ModuleScheduleRoutingModule {}
