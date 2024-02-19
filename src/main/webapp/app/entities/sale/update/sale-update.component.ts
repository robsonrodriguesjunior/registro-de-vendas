import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';
import { ICollaborator } from 'app/entities/collaborator/collaborator.model';
import { CollaboratorService } from 'app/entities/collaborator/service/collaborator.service';
import { SaleService } from '../service/sale.service';
import { ISale } from '../sale.model';
import { SaleFormService, SaleFormGroup } from './sale-form.service';

@Component({
  standalone: true,
  selector: 'jhi-sale-update',
  templateUrl: './sale-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SaleUpdateComponent implements OnInit {
  isSaving = false;
  sale: ISale | null = null;

  clientsSharedCollection: IClient[] = [];
  collaboratorsSharedCollection: ICollaborator[] = [];

  editForm: SaleFormGroup = this.saleFormService.createSaleFormGroup();

  constructor(
    protected saleService: SaleService,
    protected saleFormService: SaleFormService,
    protected clientService: ClientService,
    protected collaboratorService: CollaboratorService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareClient = (o1: IClient | null, o2: IClient | null): boolean => this.clientService.compareClient(o1, o2);

  compareCollaborator = (o1: ICollaborator | null, o2: ICollaborator | null): boolean =>
    this.collaboratorService.compareCollaborator(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sale }) => {
      this.sale = sale;
      if (sale) {
        this.updateForm(sale);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sale = this.saleFormService.getSale(this.editForm);
    if (sale.id !== null) {
      this.subscribeToSaveResponse(this.saleService.update(sale));
    } else {
      this.subscribeToSaveResponse(this.saleService.create(sale));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISale>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(sale: ISale): void {
    this.sale = sale;
    this.saleFormService.resetForm(this.editForm, sale);

    this.clientsSharedCollection = this.clientService.addClientToCollectionIfMissing<IClient>(this.clientsSharedCollection, sale.client);
    this.collaboratorsSharedCollection = this.collaboratorService.addCollaboratorToCollectionIfMissing<ICollaborator>(
      this.collaboratorsSharedCollection,
      sale.seller,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.clientService
      .query()
      .pipe(map((res: HttpResponse<IClient[]>) => res.body ?? []))
      .pipe(map((clients: IClient[]) => this.clientService.addClientToCollectionIfMissing<IClient>(clients, this.sale?.client)))
      .subscribe((clients: IClient[]) => (this.clientsSharedCollection = clients));

    this.collaboratorService
      .query()
      .pipe(map((res: HttpResponse<ICollaborator[]>) => res.body ?? []))
      .pipe(
        map((collaborators: ICollaborator[]) =>
          this.collaboratorService.addCollaboratorToCollectionIfMissing<ICollaborator>(collaborators, this.sale?.seller),
        ),
      )
      .subscribe((collaborators: ICollaborator[]) => (this.collaboratorsSharedCollection = collaborators));
  }
}
