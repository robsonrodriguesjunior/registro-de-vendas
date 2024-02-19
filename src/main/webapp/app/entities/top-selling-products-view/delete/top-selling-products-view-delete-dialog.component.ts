import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITopSellingProductsView } from '../top-selling-products-view.model';
import { TopSellingProductsViewService } from '../service/top-selling-products-view.service';

@Component({
  standalone: true,
  templateUrl: './top-selling-products-view-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TopSellingProductsViewDeleteDialogComponent {
  topSellingProductsView?: ITopSellingProductsView;

  constructor(
    protected topSellingProductsViewService: TopSellingProductsViewService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.topSellingProductsViewService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
