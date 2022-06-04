import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ClassroomTypeComponent } from '../list/classroom-type.component';
import { ClassroomTypeDetailComponent } from '../detail/classroom-type-detail.component';
import { ClassroomTypeUpdateComponent } from '../update/classroom-type-update.component';
import { ClassroomTypeRoutingResolveService } from './classroom-type-routing-resolve.service';

const classroomTypeRoute: Routes = [
  {
    path: '',
    component: ClassroomTypeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClassroomTypeDetailComponent,
    resolve: {
      classroomType: ClassroomTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClassroomTypeUpdateComponent,
    resolve: {
      classroomType: ClassroomTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClassroomTypeUpdateComponent,
    resolve: {
      classroomType: ClassroomTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(classroomTypeRoute)],
  exports: [RouterModule],
})
export class ClassroomTypeRoutingModule {}
