import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ModalityComponent } from '../list/modality.component';
import { ModalityDetailComponent } from '../detail/modality-detail.component';
import { ModalityUpdateComponent } from '../update/modality-update.component';
import { ModalityRoutingResolveService } from './modality-routing-resolve.service';

const modalityRoute: Routes = [
  {
    path: '',
    component: ModalityComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ModalityDetailComponent,
    resolve: {
      modality: ModalityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ModalityUpdateComponent,
    resolve: {
      modality: ModalityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ModalityUpdateComponent,
    resolve: {
      modality: ModalityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(modalityRoute)],
  exports: [RouterModule],
})
export class ModalityRoutingModule {}
