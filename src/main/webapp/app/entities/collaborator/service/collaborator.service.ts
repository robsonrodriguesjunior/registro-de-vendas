import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICollaborator, NewCollaborator } from '../collaborator.model';

export type PartialUpdateCollaborator = Partial<ICollaborator> & Pick<ICollaborator, 'id'>;

export type EntityResponseType = HttpResponse<ICollaborator>;
export type EntityArrayResponseType = HttpResponse<ICollaborator[]>;

@Injectable({ providedIn: 'root' })
export class CollaboratorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/collaborators');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(collaborator: NewCollaborator): Observable<EntityResponseType> {
    return this.http.post<ICollaborator>(this.resourceUrl, collaborator, { observe: 'response' });
  }

  update(collaborator: ICollaborator): Observable<EntityResponseType> {
    return this.http.put<ICollaborator>(`${this.resourceUrl}/${this.getCollaboratorIdentifier(collaborator)}`, collaborator, {
      observe: 'response',
    });
  }

  partialUpdate(collaborator: PartialUpdateCollaborator): Observable<EntityResponseType> {
    return this.http.patch<ICollaborator>(`${this.resourceUrl}/${this.getCollaboratorIdentifier(collaborator)}`, collaborator, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICollaborator>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICollaborator[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCollaboratorIdentifier(collaborator: Pick<ICollaborator, 'id'>): number {
    return collaborator.id;
  }

  compareCollaborator(o1: Pick<ICollaborator, 'id'> | null, o2: Pick<ICollaborator, 'id'> | null): boolean {
    return o1 && o2 ? this.getCollaboratorIdentifier(o1) === this.getCollaboratorIdentifier(o2) : o1 === o2;
  }

  addCollaboratorToCollectionIfMissing<Type extends Pick<ICollaborator, 'id'>>(
    collaboratorCollection: Type[],
    ...collaboratorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const collaborators: Type[] = collaboratorsToCheck.filter(isPresent);
    if (collaborators.length > 0) {
      const collaboratorCollectionIdentifiers = collaboratorCollection.map(
        collaboratorItem => this.getCollaboratorIdentifier(collaboratorItem)!,
      );
      const collaboratorsToAdd = collaborators.filter(collaboratorItem => {
        const collaboratorIdentifier = this.getCollaboratorIdentifier(collaboratorItem);
        if (collaboratorCollectionIdentifiers.includes(collaboratorIdentifier)) {
          return false;
        }
        collaboratorCollectionIdentifiers.push(collaboratorIdentifier);
        return true;
      });
      return [...collaboratorsToAdd, ...collaboratorCollection];
    }
    return collaboratorCollection;
  }
}
