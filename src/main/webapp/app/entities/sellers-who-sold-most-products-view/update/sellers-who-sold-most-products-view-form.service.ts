import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISellersWhoSoldMostProductsView, NewSellersWhoSoldMostProductsView } from '../sellers-who-sold-most-products-view.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISellersWhoSoldMostProductsView for edit and NewSellersWhoSoldMostProductsViewFormGroupInput for create.
 */
type SellersWhoSoldMostProductsViewFormGroupInput =
  | ISellersWhoSoldMostProductsView
  | PartialWithRequiredKeyOf<NewSellersWhoSoldMostProductsView>;

type SellersWhoSoldMostProductsViewFormDefaults = Pick<NewSellersWhoSoldMostProductsView, 'id'>;

type SellersWhoSoldMostProductsViewFormGroupContent = {
  id: FormControl<ISellersWhoSoldMostProductsView['id'] | NewSellersWhoSoldMostProductsView['id']>;
  quantity: FormControl<ISellersWhoSoldMostProductsView['quantity']>;
  position: FormControl<ISellersWhoSoldMostProductsView['position']>;
  seller: FormControl<ISellersWhoSoldMostProductsView['seller']>;
};

export type SellersWhoSoldMostProductsViewFormGroup = FormGroup<SellersWhoSoldMostProductsViewFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SellersWhoSoldMostProductsViewFormService {
  createSellersWhoSoldMostProductsViewFormGroup(
    sellersWhoSoldMostProductsView: SellersWhoSoldMostProductsViewFormGroupInput = { id: null },
  ): SellersWhoSoldMostProductsViewFormGroup {
    const sellersWhoSoldMostProductsViewRawValue = {
      ...this.getFormDefaults(),
      ...sellersWhoSoldMostProductsView,
    };
    return new FormGroup<SellersWhoSoldMostProductsViewFormGroupContent>({
      id: new FormControl(
        { value: sellersWhoSoldMostProductsViewRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      quantity: new FormControl(sellersWhoSoldMostProductsViewRawValue.quantity),
      position: new FormControl(sellersWhoSoldMostProductsViewRawValue.position),
      seller: new FormControl(sellersWhoSoldMostProductsViewRawValue.seller),
    });
  }

  getSellersWhoSoldMostProductsView(
    form: SellersWhoSoldMostProductsViewFormGroup,
  ): ISellersWhoSoldMostProductsView | NewSellersWhoSoldMostProductsView {
    return form.getRawValue() as ISellersWhoSoldMostProductsView | NewSellersWhoSoldMostProductsView;
  }

  resetForm(
    form: SellersWhoSoldMostProductsViewFormGroup,
    sellersWhoSoldMostProductsView: SellersWhoSoldMostProductsViewFormGroupInput,
  ): void {
    const sellersWhoSoldMostProductsViewRawValue = { ...this.getFormDefaults(), ...sellersWhoSoldMostProductsView };
    form.reset(
      {
        ...sellersWhoSoldMostProductsViewRawValue,
        id: { value: sellersWhoSoldMostProductsViewRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SellersWhoSoldMostProductsViewFormDefaults {
    return {
      id: null,
    };
  }
}
