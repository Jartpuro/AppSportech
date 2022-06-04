import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { YearComponent } from '../list/year.component';
import { YearDetailComponent } from '../detail/year-detail.component';
import { YearUpdateComponent } from '../update/year-update.component';
import { YearRoutingResolveService } from './year-routing-resolve.service';

const yearRoute: Routes = [
  {
    path: '',
    component: YearComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: YearDetailComponent,
    resolve: {
      year: YearRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: YearUpdateComponent,
    resolve: {
      year: YearRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: YearUpdateComponent,
    resolve: {
      year: YearRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(yearRoute)],
  exports: [RouterModule],
})
export class YearRoutingModule {}
