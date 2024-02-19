import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITopSellingProductsView, NewTopSellingProductsView } from '../top-selling-products-view.model';

export type PartialUpdateTopSellingProductsView = Partial<ITopSellingProductsView> & Pick<ITopSellingProductsView, 'id'>;

export type EntityResponseType = HttpResponse<ITopSellingProductsView>;
export type EntityArrayResponseType = HttpResponse<ITopSellingProductsView[]>;

@Injectable({ providedIn: 'root' })
export class TopSellingProductsViewService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/top-selling-products-views');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(topSellingProductsView: NewTopSellingProductsView): Observable<EntityResponseType> {
    return this.http.post<ITopSellingProductsView>(this.resourceUrl, topSellingProductsView, { observe: 'response' });
  }

  update(topSellingProductsView: ITopSellingProductsView): Observable<EntityResponseType> {
    return this.http.put<ITopSellingProductsView>(
      `${this.resourceUrl}/${this.getTopSellingProductsViewIdentifier(topSellingProductsView)}`,
      topSellingProductsView,
      { observe: 'response' },
    );
  }

  partialUpdate(topSellingProductsView: PartialUpdateTopSellingProductsView): Observable<EntityResponseType> {
    return this.http.patch<ITopSellingProductsView>(
      `${this.resourceUrl}/${this.getTopSellingProductsViewIdentifier(topSellingProductsView)}`,
      topSellingProductsView,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITopSellingProductsView>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITopSellingProductsView[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTopSellingProductsViewIdentifier(topSellingProductsView: Pick<ITopSellingProductsView, 'id'>): number {
    return topSellingProductsView.id;
  }

  compareTopSellingProductsView(o1: Pick<ITopSellingProductsView, 'id'> | null, o2: Pick<ITopSellingProductsView, 'id'> | null): boolean {
    return o1 && o2 ? this.getTopSellingProductsViewIdentifier(o1) === this.getTopSellingProductsViewIdentifier(o2) : o1 === o2;
  }

  addTopSellingProductsViewToCollectionIfMissing<Type extends Pick<ITopSellingProductsView, 'id'>>(
    topSellingProductsViewCollection: Type[],
    ...topSellingProductsViewsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const topSellingProductsViews: Type[] = topSellingProductsViewsToCheck.filter(isPresent);
    if (topSellingProductsViews.length > 0) {
      const topSellingProductsViewCollectionIdentifiers = topSellingProductsViewCollection.map(
        topSellingProductsViewItem => this.getTopSellingProductsViewIdentifier(topSellingProductsViewItem)!,
      );
      const topSellingProductsViewsToAdd = topSellingProductsViews.filter(topSellingProductsViewItem => {
        const topSellingProductsViewIdentifier = this.getTopSellingProductsViewIdentifier(topSellingProductsViewItem);
        if (topSellingProductsViewCollectionIdentifiers.includes(topSellingProductsViewIdentifier)) {
          return false;
        }
        topSellingProductsViewCollectionIdentifiers.push(topSellingProductsViewIdentifier);
        return true;
      });
      return [...topSellingProductsViewsToAdd, ...topSellingProductsViewCollection];
    }
    return topSellingProductsViewCollection;
  }
}
