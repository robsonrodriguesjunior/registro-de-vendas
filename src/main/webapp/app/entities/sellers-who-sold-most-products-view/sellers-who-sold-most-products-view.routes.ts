import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SellersWhoSoldMostProductsViewComponent } from './list/sellers-who-sold-most-products-view.component';
import { SellersWhoSoldMostProductsViewDetailComponent } from './detail/sellers-who-sold-most-products-view-detail.component';
import { SellersWhoSoldMostProductsViewUpdateComponent } from './update/sellers-who-sold-most-products-view-update.component';
import SellersWhoSoldMostProductsViewResolve from './route/sellers-who-sold-most-products-view-routing-resolve.service';

const sellersWhoSoldMostProductsViewRoute: Routes = [
  {
    path: '',
    component: SellersWhoSoldMostProductsViewComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SellersWhoSoldMostProductsViewDetailComponent,
    resolve: {
      sellersWhoSoldMostProductsView: SellersWhoSoldMostProductsViewResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SellersWhoSoldMostProductsViewUpdateComponent,
    resolve: {
      sellersWhoSoldMostProductsView: SellersWhoSoldMostProductsViewResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SellersWhoSoldMostProductsViewUpdateComponent,
    resolve: {
      sellersWhoSoldMostProductsView: SellersWhoSoldMostProductsViewResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default sellersWhoSoldMostProductsViewRoute;
