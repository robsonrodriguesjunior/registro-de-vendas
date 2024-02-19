import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITopSellingProductsView } from '../top-selling-products-view.model';
import { TopSellingProductsViewService } from '../service/top-selling-products-view.service';

export const topSellingProductsViewResolve = (route: ActivatedRouteSnapshot): Observable<null | ITopSellingProductsView> => {
  const id = route.params['id'];
  if (id) {
    return inject(TopSellingProductsViewService)
      .find(id)
      .pipe(
        mergeMap((topSellingProductsView: HttpResponse<ITopSellingProductsView>) => {
          if (topSellingProductsView.body) {
            return of(topSellingProductsView.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default topSellingProductsViewResolve;
