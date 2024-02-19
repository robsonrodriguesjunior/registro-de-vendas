import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISellersWhoEarnedMostView, NewSellersWhoEarnedMostView } from '../sellers-who-earned-most-view.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISellersWhoEarnedMostView for edit and NewSellersWhoEarnedMostViewFormGroupInput for create.
 */
type SellersWhoEarnedMostViewFormGroupInput = ISellersWhoEarnedMostView | PartialWithRequiredKeyOf<NewSellersWhoEarnedMostView>;

type SellersWhoEarnedMostViewFormDefaults = Pick<NewSellersWhoEarnedMostView, 'id'>;

type SellersWhoEarnedMostViewFormGroupContent = {
  id: FormControl<ISellersWhoEarnedMostView['id'] | NewSellersWhoEarnedMostView['id']>;
  value: FormControl<ISellersWhoEarnedMostView['value']>;
  position: FormControl<ISellersWhoEarnedMostView['position']>;
  seller: FormControl<ISellersWhoEarnedMostView['seller']>;
};

export type SellersWhoEarnedMostViewFormGroup = FormGroup<SellersWhoEarnedMostViewFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SellersWhoEarnedMostViewFormService {
  createSellersWhoEarnedMostViewFormGroup(
    sellersWhoEarnedMostView: SellersWhoEarnedMostViewFormGroupInput = { id: null },
  ): SellersWhoEarnedMostViewFormGroup {
    const sellersWhoEarnedMostViewRawValue = {
      ...this.getFormDefaults(),
      ...sellersWhoEarnedMostView,
    };
    return new FormGroup<SellersWhoEarnedMostViewFormGroupContent>({
      id: new FormControl(
        { value: sellersWhoEarnedMostViewRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      value: new FormControl(sellersWhoEarnedMostViewRawValue.value),
      position: new FormControl(sellersWhoEarnedMostViewRawValue.position),
      seller: new FormControl(sellersWhoEarnedMostViewRawValue.seller),
    });
  }

  getSellersWhoEarnedMostView(form: SellersWhoEarnedMostViewFormGroup): ISellersWhoEarnedMostView | NewSellersWhoEarnedMostView {
    return form.getRawValue() as ISellersWhoEarnedMostView | NewSellersWhoEarnedMostView;
  }

  resetForm(form: SellersWhoEarnedMostViewFormGroup, sellersWhoEarnedMostView: SellersWhoEarnedMostViewFormGroupInput): void {
    const sellersWhoEarnedMostViewRawValue = { ...this.getFormDefaults(), ...sellersWhoEarnedMostView };
    form.reset(
      {
        ...sellersWhoEarnedMostViewRawValue,
        id: { value: sellersWhoEarnedMostViewRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SellersWhoEarnedMostViewFormDefaults {
    return {
      id: null,
    };
  }
}
