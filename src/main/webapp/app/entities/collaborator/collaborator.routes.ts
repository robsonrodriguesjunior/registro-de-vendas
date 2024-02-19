import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CollaboratorComponent } from './list/collaborator.component';
import { CollaboratorDetailComponent } from './detail/collaborator-detail.component';
import { CollaboratorUpdateComponent } from './update/collaborator-update.component';
import CollaboratorResolve from './route/collaborator-routing-resolve.service';

const collaboratorRoute: Routes = [
  {
    path: '',
    component: CollaboratorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CollaboratorDetailComponent,
    resolve: {
      collaborator: CollaboratorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CollaboratorUpdateComponent,
    resolve: {
      collaborator: CollaboratorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CollaboratorUpdateComponent,
    resolve: {
      collaborator: CollaboratorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default collaboratorRoute;
