import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICollaborator } from '../collaborator.model';
import { CollaboratorService } from '../service/collaborator.service';

@Component({
  standalone: true,
  templateUrl: './collaborator-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CollaboratorDeleteDialogComponent {
  collaborator?: ICollaborator;

  constructor(
    protected collaboratorService: CollaboratorService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.collaboratorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
