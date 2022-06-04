import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CourseModuleComponent } from '../list/course-module.component';
import { CourseModuleDetailComponent } from '../detail/course-module-detail.component';
import { CourseModuleUpdateComponent } from '../update/course-module-update.component';
import { CourseModuleRoutingResolveService } from './course-module-routing-resolve.service';

const courseModuleRoute: Routes = [
  {
    path: '',
    component: CourseModuleComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CourseModuleDetailComponent,
    resolve: {
      courseModule: CourseModuleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CourseModuleUpdateComponent,
    resolve: {
      courseModule: CourseModuleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CourseModuleUpdateComponent,
    resolve: {
      courseModule: CourseModuleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(courseModuleRoute)],
  exports: [RouterModule],
})
export class CourseModuleRoutingModule {}
