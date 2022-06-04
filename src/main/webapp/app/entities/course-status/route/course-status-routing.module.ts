import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CourseStatusComponent } from '../list/course-status.component';
import { CourseStatusDetailComponent } from '../detail/course-status-detail.component';
import { CourseStatusUpdateComponent } from '../update/course-status-update.component';
import { CourseStatusRoutingResolveService } from './course-status-routing-resolve.service';

const courseStatusRoute: Routes = [
  {
    path: '',
    component: CourseStatusComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CourseStatusDetailComponent,
    resolve: {
      courseStatus: CourseStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CourseStatusUpdateComponent,
    resolve: {
      courseStatus: CourseStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CourseStatusUpdateComponent,
    resolve: {
      courseStatus: CourseStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(courseStatusRoute)],
  exports: [RouterModule],
})
export class CourseStatusRoutingModule {}
