import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TopSellingProductsViewComponent } from './list/top-selling-products-view.component';
import { TopSellingProductsViewDetailComponent } from './detail/top-selling-products-view-detail.component';
import { TopSellingProductsViewUpdateComponent } from './update/top-selling-products-view-update.component';
import TopSellingProductsViewResolve from './route/top-selling-products-view-routing-resolve.service';

const topSellingProductsViewRoute: Routes = [
  {
    path: '',
    component: TopSellingProductsViewComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TopSellingProductsViewDetailComponent,
    resolve: {
      topSellingProductsView: TopSellingProductsViewResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TopSellingProductsViewUpdateComponent,
    resolve: {
      topSellingProductsView: TopSellingProductsViewResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TopSellingProductsViewUpdateComponent,
    resolve: {
      topSellingProductsView: TopSellingProductsViewResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default topSellingProductsViewRoute;
