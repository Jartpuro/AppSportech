import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DayComponent } from '../list/day.component';
import { DayDetailComponent } from '../detail/day-detail.component';
import { DayUpdateComponent } from '../update/day-update.component';
import { DayRoutingResolveService } from './day-routing-resolve.service';

const dayRoute: Routes = [
  {
    path: '',
    component: DayComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DayDetailComponent,
    resolve: {
      day: DayRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DayUpdateComponent,
    resolve: {
      day: DayRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DayUpdateComponent,
    resolve: {
      day: DayRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dayRoute)],
  exports: [RouterModule],
})
export class DayRoutingModule {}
