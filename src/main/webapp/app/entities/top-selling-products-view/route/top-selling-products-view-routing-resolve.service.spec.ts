import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITopSellingProductsView } from '../top-selling-products-view.model';
import { TopSellingProductsViewService } from '../service/top-selling-products-view.service';

import topSellingProductsViewResolve from './top-selling-products-view-routing-resolve.service';

describe('TopSellingProductsView routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: TopSellingProductsViewService;
  let resultTopSellingProductsView: ITopSellingProductsView | null | undefined;

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
    service = TestBed.inject(TopSellingProductsViewService);
    resultTopSellingProductsView = undefined;
  });

  describe('resolve', () => {
    it('should return ITopSellingProductsView returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        topSellingProductsViewResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTopSellingProductsView = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTopSellingProductsView).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        topSellingProductsViewResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTopSellingProductsView = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTopSellingProductsView).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ITopSellingProductsView>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        topSellingProductsViewResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTopSellingProductsView = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTopSellingProductsView).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
