import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../sellers-who-earned-most-view.test-samples';

import { SellersWhoEarnedMostViewFormService } from './sellers-who-earned-most-view-form.service';

describe('SellersWhoEarnedMostView Form Service', () => {
  let service: SellersWhoEarnedMostViewFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SellersWhoEarnedMostViewFormService);
  });

  describe('Service methods', () => {
    describe('createSellersWhoEarnedMostViewFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSellersWhoEarnedMostViewFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            value: expect.any(Object),
            position: expect.any(Object),
            seller: expect.any(Object),
          }),
        );
      });

      it('passing ISellersWhoEarnedMostView should create a new form with FormGroup', () => {
        const formGroup = service.createSellersWhoEarnedMostViewFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            value: expect.any(Object),
            position: expect.any(Object),
            seller: expect.any(Object),
          }),
        );
      });
    });

    describe('getSellersWhoEarnedMostView', () => {
      it('should return NewSellersWhoEarnedMostView for default SellersWhoEarnedMostView initial value', () => {
        const formGroup = service.createSellersWhoEarnedMostViewFormGroup(sampleWithNewData);

        const sellersWhoEarnedMostView = service.getSellersWhoEarnedMostView(formGroup) as any;

        expect(sellersWhoEarnedMostView).toMatchObject(sampleWithNewData);
      });

      it('should return NewSellersWhoEarnedMostView for empty SellersWhoEarnedMostView initial value', () => {
        const formGroup = service.createSellersWhoEarnedMostViewFormGroup();

        const sellersWhoEarnedMostView = service.getSellersWhoEarnedMostView(formGroup) as any;

        expect(sellersWhoEarnedMostView).toMatchObject({});
      });

      it('should return ISellersWhoEarnedMostView', () => {
        const formGroup = service.createSellersWhoEarnedMostViewFormGroup(sampleWithRequiredData);

        const sellersWhoEarnedMostView = service.getSellersWhoEarnedMostView(formGroup) as any;

        expect(sellersWhoEarnedMostView).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISellersWhoEarnedMostView should not enable id FormControl', () => {
        const formGroup = service.createSellersWhoEarnedMostViewFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSellersWhoEarnedMostView should disable id FormControl', () => {
        const formGroup = service.createSellersWhoEarnedMostViewFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
