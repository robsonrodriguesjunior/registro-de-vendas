import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SellersWhoEarnedMostViewComponent } from './list/sellers-who-earned-most-view.component';
import { SellersWhoEarnedMostViewDetailComponent } from './detail/sellers-who-earned-most-view-detail.component';
import { SellersWhoEarnedMostViewUpdateComponent } from './update/sellers-who-earned-most-view-update.component';
import SellersWhoEarnedMostViewResolve from './route/sellers-who-earned-most-view-routing-resolve.service';

const sellersWhoEarnedMostViewRoute: Routes = [
  {
    path: '',
    component: SellersWhoEarnedMostViewComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SellersWhoEarnedMostViewDetailComponent,
    resolve: {
      sellersWhoEarnedMostView: SellersWhoEarnedMostViewResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SellersWhoEarnedMostViewUpdateComponent,
    resolve: {
      sellersWhoEarnedMostView: SellersWhoEarnedMostViewResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SellersWhoEarnedMostViewUpdateComponent,
    resolve: {
      sellersWhoEarnedMostView: SellersWhoEarnedMostViewResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default sellersWhoEarnedMostViewRoute;
