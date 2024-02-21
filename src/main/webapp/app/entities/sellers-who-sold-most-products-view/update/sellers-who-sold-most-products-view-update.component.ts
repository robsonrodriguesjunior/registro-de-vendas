import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICollaborator } from 'app/entities/collaborator/collaborator.model';
import { CollaboratorService } from 'app/entities/collaborator/service/collaborator.service';
import { ISellersWhoSoldMostProductsView } from '../sellers-who-sold-most-products-view.model';
import { SellersWhoSoldMostProductsViewService } from '../service/sellers-who-sold-most-products-view.service';
import {
  SellersWhoSoldMostProductsViewFormService,
  SellersWhoSoldMostProductsViewFormGroup,
} from './sellers-who-sold-most-products-view-form.service';

@Component({
  standalone: true,
  selector: 'jhi-sellers-who-sold-most-products-view-update',
  templateUrl: './sellers-who-sold-most-products-view-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SellersWhoSoldMostProductsViewUpdateComponent implements OnInit {
  isSaving = false;
  sellersWhoSoldMostProductsView: ISellersWhoSoldMostProductsView | null = null;

  sellersCollection: ICollaborator[] = [];

  editForm: SellersWhoSoldMostProductsViewFormGroup =
    this.sellersWhoSoldMostProductsViewFormService.createSellersWhoSoldMostProductsViewFormGroup();

  constructor(
    protected sellersWhoSoldMostProductsViewService: SellersWhoSoldMostProductsViewService,
    protected sellersWhoSoldMostProductsViewFormService: SellersWhoSoldMostProductsViewFormService,
    protected collaboratorService: CollaboratorService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCollaborator = (o1: ICollaborator | null, o2: ICollaborator | null): boolean =>
    this.collaboratorService.compareCollaborator(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sellersWhoSoldMostProductsView }) => {
      this.sellersWhoSoldMostProductsView = sellersWhoSoldMostProductsView;
      if (sellersWhoSoldMostProductsView) {
        this.updateForm(sellersWhoSoldMostProductsView);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sellersWhoSoldMostProductsView = this.sellersWhoSoldMostProductsViewFormService.getSellersWhoSoldMostProductsView(this.editForm);
    if (sellersWhoSoldMostProductsView.id !== null) {
      this.subscribeToSaveResponse(this.sellersWhoSoldMostProductsViewService.update(sellersWhoSoldMostProductsView));
    } else {
      this.subscribeToSaveResponse(this.sellersWhoSoldMostProductsViewService.create(sellersWhoSoldMostProductsView));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISellersWhoSoldMostProductsView>>): void {
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

  protected updateForm(sellersWhoSoldMostProductsView: ISellersWhoSoldMostProductsView): void {
    this.sellersWhoSoldMostProductsView = sellersWhoSoldMostProductsView;
    this.sellersWhoSoldMostProductsViewFormService.resetForm(this.editForm, sellersWhoSoldMostProductsView);

    this.sellersCollection = this.collaboratorService.addCollaboratorToCollectionIfMissing<ICollaborator>(
      this.sellersCollection,
      sellersWhoSoldMostProductsView.seller,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.collaboratorService
      .query({ 'sellersWhoSoldMostProductsViewId.specified': 'false' })
      .pipe(map((res: HttpResponse<ICollaborator[]>) => res.body ?? []))
      .pipe(
        map((collaborators: ICollaborator[]) =>
          this.collaboratorService.addCollaboratorToCollectionIfMissing<ICollaborator>(
            collaborators,
            this.sellersWhoSoldMostProductsView?.seller,
          ),
        ),
      )
      .subscribe((collaborators: ICollaborator[]) => (this.sellersCollection = collaborators));
  }
}
