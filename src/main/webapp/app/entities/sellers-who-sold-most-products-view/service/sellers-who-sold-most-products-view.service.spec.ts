import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISellersWhoSoldMostProductsView } from '../sellers-who-sold-most-products-view.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../sellers-who-sold-most-products-view.test-samples';

import { SellersWhoSoldMostProductsViewService } from './sellers-who-sold-most-products-view.service';

const requireRestSample: ISellersWhoSoldMostProductsView = {
  ...sampleWithRequiredData,
};

describe('SellersWhoSoldMostProductsView Service', () => {
  let service: SellersWhoSoldMostProductsViewService;
  let httpMock: HttpTestingController;
  let expectedResult: ISellersWhoSoldMostProductsView | ISellersWhoSoldMostProductsView[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SellersWhoSoldMostProductsViewService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a SellersWhoSoldMostProductsView', () => {
      const sellersWhoSoldMostProductsView = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(sellersWhoSoldMostProductsView).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SellersWhoSoldMostProductsView', () => {
      const sellersWhoSoldMostProductsView = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(sellersWhoSoldMostProductsView).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SellersWhoSoldMostProductsView', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SellersWhoSoldMostProductsView', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SellersWhoSoldMostProductsView', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSellersWhoSoldMostProductsViewToCollectionIfMissing', () => {
      it('should add a SellersWhoSoldMostProductsView to an empty array', () => {
        const sellersWhoSoldMostProductsView: ISellersWhoSoldMostProductsView = sampleWithRequiredData;
        expectedResult = service.addSellersWhoSoldMostProductsViewToCollectionIfMissing([], sellersWhoSoldMostProductsView);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sellersWhoSoldMostProductsView);
      });

      it('should not add a SellersWhoSoldMostProductsView to an array that contains it', () => {
        const sellersWhoSoldMostProductsView: ISellersWhoSoldMostProductsView = sampleWithRequiredData;
        const sellersWhoSoldMostProductsViewCollection: ISellersWhoSoldMostProductsView[] = [
          {
            ...sellersWhoSoldMostProductsView,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSellersWhoSoldMostProductsViewToCollectionIfMissing(
          sellersWhoSoldMostProductsViewCollection,
          sellersWhoSoldMostProductsView,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SellersWhoSoldMostProductsView to an array that doesn't contain it", () => {
        const sellersWhoSoldMostProductsView: ISellersWhoSoldMostProductsView = sampleWithRequiredData;
        const sellersWhoSoldMostProductsViewCollection: ISellersWhoSoldMostProductsView[] = [sampleWithPartialData];
        expectedResult = service.addSellersWhoSoldMostProductsViewToCollectionIfMissing(
          sellersWhoSoldMostProductsViewCollection,
          sellersWhoSoldMostProductsView,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sellersWhoSoldMostProductsView);
      });

      it('should add only unique SellersWhoSoldMostProductsView to an array', () => {
        const sellersWhoSoldMostProductsViewArray: ISellersWhoSoldMostProductsView[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const sellersWhoSoldMostProductsViewCollection: ISellersWhoSoldMostProductsView[] = [sampleWithRequiredData];
        expectedResult = service.addSellersWhoSoldMostProductsViewToCollectionIfMissing(
          sellersWhoSoldMostProductsViewCollection,
          ...sellersWhoSoldMostProductsViewArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sellersWhoSoldMostProductsView: ISellersWhoSoldMostProductsView = sampleWithRequiredData;
        const sellersWhoSoldMostProductsView2: ISellersWhoSoldMostProductsView = sampleWithPartialData;
        expectedResult = service.addSellersWhoSoldMostProductsViewToCollectionIfMissing(
          [],
          sellersWhoSoldMostProductsView,
          sellersWhoSoldMostProductsView2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sellersWhoSoldMostProductsView);
        expect(expectedResult).toContain(sellersWhoSoldMostProductsView2);
      });

      it('should accept null and undefined values', () => {
        const sellersWhoSoldMostProductsView: ISellersWhoSoldMostProductsView = sampleWithRequiredData;
        expectedResult = service.addSellersWhoSoldMostProductsViewToCollectionIfMissing(
          [],
          null,
          sellersWhoSoldMostProductsView,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sellersWhoSoldMostProductsView);
      });

      it('should return initial array if no SellersWhoSoldMostProductsView is added', () => {
        const sellersWhoSoldMostProductsViewCollection: ISellersWhoSoldMostProductsView[] = [sampleWithRequiredData];
        expectedResult = service.addSellersWhoSoldMostProductsViewToCollectionIfMissing(
          sellersWhoSoldMostProductsViewCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(sellersWhoSoldMostProductsViewCollection);
      });
    });

    describe('compareSellersWhoSoldMostProductsView', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSellersWhoSoldMostProductsView(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSellersWhoSoldMostProductsView(entity1, entity2);
        const compareResult2 = service.compareSellersWhoSoldMostProductsView(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSellersWhoSoldMostProductsView(entity1, entity2);
        const compareResult2 = service.compareSellersWhoSoldMostProductsView(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSellersWhoSoldMostProductsView(entity1, entity2);
        const compareResult2 = service.compareSellersWhoSoldMostProductsView(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
