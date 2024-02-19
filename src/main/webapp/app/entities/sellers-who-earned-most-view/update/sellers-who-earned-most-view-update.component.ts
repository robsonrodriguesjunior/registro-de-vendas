import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICollaborator } from 'app/entities/collaborator/collaborator.model';
import { CollaboratorService } from 'app/entities/collaborator/service/collaborator.service';
import { ISellersWhoEarnedMostView } from '../sellers-who-earned-most-view.model';
import { SellersWhoEarnedMostViewService } from '../service/sellers-who-earned-most-view.service';
import { SellersWhoEarnedMostViewFormService, SellersWhoEarnedMostViewFormGroup } from './sellers-who-earned-most-view-form.service';

@Component({
  standalone: true,
  selector: 'jhi-sellers-who-earned-most-view-update',
  templateUrl: './sellers-who-earned-most-view-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SellersWhoEarnedMostViewUpdateComponent implements OnInit {
  isSaving = false;
  sellersWhoEarnedMostView: ISellersWhoEarnedMostView | null = null;

  sellersCollection: ICollaborator[] = [];

  editForm: SellersWhoEarnedMostViewFormGroup = this.sellersWhoEarnedMostViewFormService.createSellersWhoEarnedMostViewFormGroup();

  constructor(
    protected sellersWhoEarnedMostViewService: SellersWhoEarnedMostViewService,
    protected sellersWhoEarnedMostViewFormService: SellersWhoEarnedMostViewFormService,
    protected collaboratorService: CollaboratorService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCollaborator = (o1: ICollaborator | null, o2: ICollaborator | null): boolean =>
    this.collaboratorService.compareCollaborator(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sellersWhoEarnedMostView }) => {
      this.sellersWhoEarnedMostView = sellersWhoEarnedMostView;
      if (sellersWhoEarnedMostView) {
        this.updateForm(sellersWhoEarnedMostView);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sellersWhoEarnedMostView = this.sellersWhoEarnedMostViewFormService.getSellersWhoEarnedMostView(this.editForm);
    if (sellersWhoEarnedMostView.id !== null) {
      this.subscribeToSaveResponse(this.sellersWhoEarnedMostViewService.update(sellersWhoEarnedMostView));
    } else {
      this.subscribeToSaveResponse(this.sellersWhoEarnedMostViewService.create(sellersWhoEarnedMostView));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISellersWhoEarnedMostView>>): void {
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

  protected updateForm(sellersWhoEarnedMostView: ISellersWhoEarnedMostView): void {
    this.sellersWhoEarnedMostView = sellersWhoEarnedMostView;
    this.sellersWhoEarnedMostViewFormService.resetForm(this.editForm, sellersWhoEarnedMostView);

    this.sellersCollection = this.collaboratorService.addCollaboratorToCollectionIfMissing<ICollaborator>(
      this.sellersCollection,
      sellersWhoEarnedMostView.seller,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.collaboratorService
      .query({ filter: 'sellerswhoearnedmostview-is-null' })
      .pipe(map((res: HttpResponse<ICollaborator[]>) => res.body ?? []))
      .pipe(
        map((collaborators: ICollaborator[]) =>
          this.collaboratorService.addCollaboratorToCollectionIfMissing<ICollaborator>(
            collaborators,
            this.sellersWhoEarnedMostView?.seller,
          ),
        ),
      )
      .subscribe((collaborators: ICollaborator[]) => (this.sellersCollection = collaborators));
  }
}
