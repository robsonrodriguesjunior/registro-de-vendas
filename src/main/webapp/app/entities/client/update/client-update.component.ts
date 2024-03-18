import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import SharedModule from 'app/shared/shared.module';

import { AlertService } from 'app/core/util/alert.service';
import { IClient } from '../client.model';
import { ClientService } from '../service/client.service';

@Component({
  standalone: true,
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ClientUpdateComponent implements OnInit {
  isSaving = false;
  client: IClient = {};

  constructor(
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute,
    private alertService: AlertService,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ client }) => {
      if (client) {
        this.client = client;
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    if (this.client.id) {
      this.subscribeToSaveResponse(this.clientService.update(this.client));
      this.alertService.addAlert({ type: 'danger', message: 'Salvou' });
    } else {
      this.subscribeToSaveResponse(this.clientService.create(this.client));
      this.alertService.addAlert({ type: 'danger', message: 'Ocorreu um erro' });
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>): void {
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

  private isNew(): boolean {
    return !!this.client.id;
  }
}
