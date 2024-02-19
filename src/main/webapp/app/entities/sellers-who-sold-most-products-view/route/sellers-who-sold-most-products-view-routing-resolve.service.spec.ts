import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISellersWhoSoldMostProductsView } from '../sellers-who-sold-most-products-view.model';
import { SellersWhoSoldMostProductsViewService } from '../service/sellers-who-sold-most-products-view.service';

import sellersWhoSoldMostProductsViewResolve from './sellers-who-sold-most-products-view-routing-resolve.service';

describe('SellersWhoSoldMostProductsView routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: SellersWhoSoldMostProductsViewService;
  let resultSellersWhoSoldMostProductsView: ISellersWhoSoldMostProductsView | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(SellersWhoSoldMostProductsViewService);
    resultSellersWhoSoldMostProductsView = undefined;
  });

  describe('resolve', () => {
    it('should return ISellersWhoSoldMostProductsView returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        sellersWhoSoldMostProductsViewResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSellersWhoSoldMostProductsView = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSellersWhoSoldMostProductsView).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        sellersWhoSoldMostProductsViewResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSellersWhoSoldMostProductsView = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSellersWhoSoldMostProductsView).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISellersWhoSoldMostProductsView>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        sellersWhoSoldMostProductsViewResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSellersWhoSoldMostProductsView = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSellersWhoSoldMostProductsView).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
