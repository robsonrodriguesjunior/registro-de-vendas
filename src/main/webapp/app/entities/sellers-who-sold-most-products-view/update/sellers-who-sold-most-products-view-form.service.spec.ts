import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../sellers-who-sold-most-products-view.test-samples';

import { SellersWhoSoldMostProductsViewFormService } from './sellers-who-sold-most-products-view-form.service';

describe('SellersWhoSoldMostProductsView Form Service', () => {
  let service: SellersWhoSoldMostProductsViewFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SellersWhoSoldMostProductsViewFormService);
  });

  describe('Service methods', () => {
    describe('createSellersWhoSoldMostProductsViewFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSellersWhoSoldMostProductsViewFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quantity: expect.any(Object),
            position: expect.any(Object),
            seller: expect.any(Object),
          }),
        );
      });

      it('passing ISellersWhoSoldMostProductsView should create a new form with FormGroup', () => {
        const formGroup = service.createSellersWhoSoldMostProductsViewFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quantity: expect.any(Object),
            position: expect.any(Object),
            seller: expect.any(Object),
          }),
        );
      });
    });

    describe('getSellersWhoSoldMostProductsView', () => {
      it('should return NewSellersWhoSoldMostProductsView for default SellersWhoSoldMostProductsView initial value', () => {
        const formGroup = service.createSellersWhoSoldMostProductsViewFormGroup(sampleWithNewData);

        const sellersWhoSoldMostProductsView = service.getSellersWhoSoldMostProductsView(formGroup) as any;

        expect(sellersWhoSoldMostProductsView).toMatchObject(sampleWithNewData);
      });

      it('should return NewSellersWhoSoldMostProductsView for empty SellersWhoSoldMostProductsView initial value', () => {
        const formGroup = service.createSellersWhoSoldMostProductsViewFormGroup();

        const sellersWhoSoldMostProductsView = service.getSellersWhoSoldMostProductsView(formGroup) as any;

        expect(sellersWhoSoldMostProductsView).toMatchObject({});
      });

      it('should return ISellersWhoSoldMostProductsView', () => {
        const formGroup = service.createSellersWhoSoldMostProductsViewFormGroup(sampleWithRequiredData);

        const sellersWhoSoldMostProductsView = service.getSellersWhoSoldMostProductsView(formGroup) as any;

        expect(sellersWhoSoldMostProductsView).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISellersWhoSoldMostProductsView should not enable id FormControl', () => {
        const formGroup = service.createSellersWhoSoldMostProductsViewFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSellersWhoSoldMostProductsView should disable id FormControl', () => {
        const formGroup = service.createSellersWhoSoldMostProductsViewFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
