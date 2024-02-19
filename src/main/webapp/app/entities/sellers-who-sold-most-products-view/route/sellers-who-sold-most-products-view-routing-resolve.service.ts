import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISellersWhoSoldMostProductsView } from '../sellers-who-sold-most-products-view.model';
import { SellersWhoSoldMostProductsViewService } from '../service/sellers-who-sold-most-products-view.service';

export const sellersWhoSoldMostProductsViewResolve = (
  route: ActivatedRouteSnapshot,
): Observable<null | ISellersWhoSoldMostProductsView> => {
  const id = route.params['id'];
  if (id) {
    return inject(SellersWhoSoldMostProductsViewService)
      .find(id)
      .pipe(
        mergeMap((sellersWhoSoldMostProductsView: HttpResponse<ISellersWhoSoldMostProductsView>) => {
          if (sellersWhoSoldMostProductsView.body) {
            return of(sellersWhoSoldMostProductsView.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default sellersWhoSoldMostProductsViewResolve;
