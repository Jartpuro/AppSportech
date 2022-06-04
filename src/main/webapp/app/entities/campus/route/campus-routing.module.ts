import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CampusComponent } from '../list/campus.component';
import { CampusDetailComponent } from '../detail/campus-detail.component';
import { CampusUpdateComponent } from '../update/campus-update.component';
import { CampusRoutingResolveService } from './campus-routing-resolve.service';

const campusRoute: Routes = [
  {
    path: '',
    component: CampusComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CampusDetailComponent,
    resolve: {
      campus: CampusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CampusUpdateComponent,
    resolve: {
      campus: CampusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CampusUpdateComponent,
    resolve: {
      campus: CampusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(campusRoute)],
  exports: [RouterModule],
})
export class CampusRoutingModule {}
