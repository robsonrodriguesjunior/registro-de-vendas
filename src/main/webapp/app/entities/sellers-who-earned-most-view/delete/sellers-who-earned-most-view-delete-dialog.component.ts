import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISellersWhoEarnedMostView } from '../sellers-who-earned-most-view.model';
import { SellersWhoEarnedMostViewService } from '../service/sellers-who-earned-most-view.service';

@Component({
  standalone: true,
  templateUrl: './sellers-who-earned-most-view-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SellersWhoEarnedMostViewDeleteDialogComponent {
  sellersWhoEarnedMostView?: ISellersWhoEarnedMostView;

  constructor(
    protected sellersWhoEarnedMostViewService: SellersWhoEarnedMostViewService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sellersWhoEarnedMostViewService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
