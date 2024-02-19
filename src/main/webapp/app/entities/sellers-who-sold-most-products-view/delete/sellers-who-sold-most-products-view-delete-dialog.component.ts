import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISellersWhoSoldMostProductsView } from '../sellers-who-sold-most-products-view.model';
import { SellersWhoSoldMostProductsViewService } from '../service/sellers-who-sold-most-products-view.service';

@Component({
  standalone: true,
  templateUrl: './sellers-who-sold-most-products-view-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SellersWhoSoldMostProductsViewDeleteDialogComponent {
  sellersWhoSoldMostProductsView?: ISellersWhoSoldMostProductsView;

  constructor(
    protected sellersWhoSoldMostProductsViewService: SellersWhoSoldMostProductsViewService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sellersWhoSoldMostProductsViewService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
