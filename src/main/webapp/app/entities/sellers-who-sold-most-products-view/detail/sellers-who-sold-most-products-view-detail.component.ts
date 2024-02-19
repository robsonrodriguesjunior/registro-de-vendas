import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ISellersWhoSoldMostProductsView } from '../sellers-who-sold-most-products-view.model';

@Component({
  standalone: true,
  selector: 'jhi-sellers-who-sold-most-products-view-detail',
  templateUrl: './sellers-who-sold-most-products-view-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SellersWhoSoldMostProductsViewDetailComponent {
  @Input() sellersWhoSoldMostProductsView: ISellersWhoSoldMostProductsView | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
