import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LogAuditComponent } from '../list/log-audit.component';
import { LogAuditDetailComponent } from '../detail/log-audit-detail.component';
import { LogAuditUpdateComponent } from '../update/log-audit-update.component';
import { LogAuditRoutingResolveService } from './log-audit-routing-resolve.service';

const logAuditRoute: Routes = [
  {
    path: '',
    component: LogAuditComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LogAuditDetailComponent,
    resolve: {
      logAudit: LogAuditRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LogAuditUpdateComponent,
    resolve: {
      logAudit: LogAuditRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LogAuditUpdateComponent,
    resolve: {
      logAudit: LogAuditRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(logAuditRoute)],
  exports: [RouterModule],
})
export class LogAuditRoutingModule {}
