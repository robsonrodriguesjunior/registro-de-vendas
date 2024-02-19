import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITopSellingProductsView } from '../top-selling-products-view.model';

@Component({
  standalone: true,
  selector: 'jhi-top-selling-products-view-detail',
  templateUrl: './top-selling-products-view-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TopSellingProductsViewDetailComponent {
  @Input() topSellingProductsView: ITopSellingProductsView | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
