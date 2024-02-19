import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../top-selling-products-view.test-samples';

import { TopSellingProductsViewFormService } from './top-selling-products-view-form.service';

describe('TopSellingProductsView Form Service', () => {
  let service: TopSellingProductsViewFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TopSellingProductsViewFormService);
  });

  describe('Service methods', () => {
    describe('createTopSellingProductsViewFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTopSellingProductsViewFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quantity: expect.any(Object),
            position: expect.any(Object),
            product: expect.any(Object),
          }),
        );
      });

      it('passing ITopSellingProductsView should create a new form with FormGroup', () => {
        const formGroup = service.createTopSellingProductsViewFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            quantity: expect.any(Object),
            position: expect.any(Object),
            product: expect.any(Object),
          }),
        );
      });
    });

    describe('getTopSellingProductsView', () => {
      it('should return NewTopSellingProductsView for default TopSellingProductsView initial value', () => {
        const formGroup = service.createTopSellingProductsViewFormGroup(sampleWithNewData);

        const topSellingProductsView = service.getTopSellingProductsView(formGroup) as any;

        expect(topSellingProductsView).toMatchObject(sampleWithNewData);
      });

      it('should return NewTopSellingProductsView for empty TopSellingProductsView initial value', () => {
        const formGroup = service.createTopSellingProductsViewFormGroup();

        const topSellingProductsView = service.getTopSellingProductsView(formGroup) as any;

        expect(topSellingProductsView).toMatchObject({});
      });

      it('should return ITopSellingProductsView', () => {
        const formGroup = service.createTopSellingProductsViewFormGroup(sampleWithRequiredData);

        const topSellingProductsView = service.getTopSellingProductsView(formGroup) as any;

        expect(topSellingProductsView).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITopSellingProductsView should not enable id FormControl', () => {
        const formGroup = service.createTopSellingProductsViewFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTopSellingProductsView should disable id FormControl', () => {
        const formGroup = service.createTopSellingProductsViewFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
