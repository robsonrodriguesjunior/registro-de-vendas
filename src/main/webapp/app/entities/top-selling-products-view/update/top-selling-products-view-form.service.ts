import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITopSellingProductsView, NewTopSellingProductsView } from '../top-selling-products-view.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITopSellingProductsView for edit and NewTopSellingProductsViewFormGroupInput for create.
 */
type TopSellingProductsViewFormGroupInput = ITopSellingProductsView | PartialWithRequiredKeyOf<NewTopSellingProductsView>;

type TopSellingProductsViewFormDefaults = Pick<NewTopSellingProductsView, 'id'>;

type TopSellingProductsViewFormGroupContent = {
  id: FormControl<ITopSellingProductsView['id'] | NewTopSellingProductsView['id']>;
  quantity: FormControl<ITopSellingProductsView['quantity']>;
  position: FormControl<ITopSellingProductsView['position']>;
  product: FormControl<ITopSellingProductsView['product']>;
};

export type TopSellingProductsViewFormGroup = FormGroup<TopSellingProductsViewFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TopSellingProductsViewFormService {
  createTopSellingProductsViewFormGroup(
    topSellingProductsView: TopSellingProductsViewFormGroupInput = { id: null },
  ): TopSellingProductsViewFormGroup {
    const topSellingProductsViewRawValue = {
      ...this.getFormDefaults(),
      ...topSellingProductsView,
    };
    return new FormGroup<TopSellingProductsViewFormGroupContent>({
      id: new FormControl(
        { value: topSellingProductsViewRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      quantity: new FormControl(topSellingProductsViewRawValue.quantity),
      position: new FormControl(topSellingProductsViewRawValue.position),
      product: new FormControl(topSellingProductsViewRawValue.product),
    });
  }

  getTopSellingProductsView(form: TopSellingProductsViewFormGroup): ITopSellingProductsView | NewTopSellingProductsView {
    return form.getRawValue() as ITopSellingProductsView | NewTopSellingProductsView;
  }

  resetForm(form: TopSellingProductsViewFormGroup, topSellingProductsView: TopSellingProductsViewFormGroupInput): void {
    const topSellingProductsViewRawValue = { ...this.getFormDefaults(), ...topSellingProductsView };
    form.reset(
      {
        ...topSellingProductsViewRawValue,
        id: { value: topSellingProductsViewRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TopSellingProductsViewFormDefaults {
    return {
      id: null,
    };
  }
}
