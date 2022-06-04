import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LogErrorComponent } from '../list/log-error.component';
import { LogErrorDetailComponent } from '../detail/log-error-detail.component';
import { LogErrorUpdateComponent } from '../update/log-error-update.component';
import { LogErrorRoutingResolveService } from './log-error-routing-resolve.service';

const logErrorRoute: Routes = [
  {
    path: '',
    component: LogErrorComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LogErrorDetailComponent,
    resolve: {
      logError: LogErrorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LogErrorUpdateComponent,
    resolve: {
      logError: LogErrorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LogErrorUpdateComponent,
    resolve: {
      logError: LogErrorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(logErrorRoute)],
  exports: [RouterModule],
})
export class LogErrorRoutingModule {}
