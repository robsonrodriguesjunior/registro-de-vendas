import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ISellersWhoEarnedMostView } from '../sellers-who-earned-most-view.model';

@Component({
  standalone: true,
  selector: 'jhi-sellers-who-earned-most-view-detail',
  templateUrl: './sellers-who-earned-most-view-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SellersWhoEarnedMostViewDetailComponent {
  @Input() sellersWhoEarnedMostView: ISellersWhoEarnedMostView | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
