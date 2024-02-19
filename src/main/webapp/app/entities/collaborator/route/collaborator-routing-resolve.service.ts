import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICollaborator } from '../collaborator.model';
import { CollaboratorService } from '../service/collaborator.service';

export const collaboratorResolve = (route: ActivatedRouteSnapshot): Observable<null | ICollaborator> => {
  const id = route.params['id'];
  if (id) {
    return inject(CollaboratorService)
      .find(id)
      .pipe(
        mergeMap((collaborator: HttpResponse<ICollaborator>) => {
          if (collaborator.body) {
            return of(collaborator.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default collaboratorResolve;
