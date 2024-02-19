import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';
import { CollaboratorType } from 'app/entities/enumerations/collaborator-type.model';
import { CollaboratorStatus } from 'app/entities/enumerations/collaborator-status.model';
import { CollaboratorService } from '../service/collaborator.service';
import { ICollaborator } from '../collaborator.model';
import { CollaboratorFormService, CollaboratorFormGroup } from './collaborator-form.service';

@Component({
  standalone: true,
  selector: 'jhi-collaborator-update',
  templateUrl: './collaborator-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CollaboratorUpdateComponent implements OnInit {
  isSaving = false;
  collaborator: ICollaborator | null = null;
  collaboratorTypeValues = Object.keys(CollaboratorType);
  collaboratorStatusValues = Object.keys(CollaboratorStatus);

  peopleCollection: IPerson[] = [];

  editForm: CollaboratorFormGroup = this.collaboratorFormService.createCollaboratorFormGroup();

  constructor(
    protected collaboratorService: CollaboratorService,
    protected collaboratorFormService: CollaboratorFormService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  comparePerson = (o1: IPerson | null, o2: IPerson | null): boolean => this.personService.comparePerson(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collaborator }) => {
      this.collaborator = collaborator;
      if (collaborator) {
        this.updateForm(collaborator);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const collaborator = this.collaboratorFormService.getCollaborator(this.editForm);
    if (collaborator.id !== null) {
      this.subscribeToSaveResponse(this.collaboratorService.update(collaborator));
    } else {
      this.subscribeToSaveResponse(this.collaboratorService.create(collaborator));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICollaborator>>): void {
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

  protected updateForm(collaborator: ICollaborator): void {
    this.collaborator = collaborator;
    this.collaboratorFormService.resetForm(this.editForm, collaborator);

    this.peopleCollection = this.personService.addPersonToCollectionIfMissing<IPerson>(this.peopleCollection, collaborator.person);
  }

  protected loadRelationshipsOptions(): void {
    this.personService
      .query({ filter: 'seller-is-null' })
      .pipe(map((res: HttpResponse<IPerson[]>) => res.body ?? []))
      .pipe(map((people: IPerson[]) => this.personService.addPersonToCollectionIfMissing<IPerson>(people, this.collaborator?.person)))
      .subscribe((people: IPerson[]) => (this.peopleCollection = people));
  }
}
