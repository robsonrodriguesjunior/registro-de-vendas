import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISellersWhoEarnedMostView, NewSellersWhoEarnedMostView } from '../sellers-who-earned-most-view.model';

export type PartialUpdateSellersWhoEarnedMostView = Partial<ISellersWhoEarnedMostView> & Pick<ISellersWhoEarnedMostView, 'id'>;

export type EntityResponseType = HttpResponse<ISellersWhoEarnedMostView>;
export type EntityArrayResponseType = HttpResponse<ISellersWhoEarnedMostView[]>;

@Injectable({ providedIn: 'root' })
export class SellersWhoEarnedMostViewService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sellers-who-earned-most-views');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(sellersWhoEarnedMostView: NewSellersWhoEarnedMostView): Observable<EntityResponseType> {
    return this.http.post<ISellersWhoEarnedMostView>(this.resourceUrl, sellersWhoEarnedMostView, { observe: 'response' });
  }

  update(sellersWhoEarnedMostView: ISellersWhoEarnedMostView): Observable<EntityResponseType> {
    return this.http.put<ISellersWhoEarnedMostView>(
      `${this.resourceUrl}/${this.getSellersWhoEarnedMostViewIdentifier(sellersWhoEarnedMostView)}`,
      sellersWhoEarnedMostView,
      { observe: 'response' },
    );
  }

  partialUpdate(sellersWhoEarnedMostView: PartialUpdateSellersWhoEarnedMostView): Observable<EntityResponseType> {
    return this.http.patch<ISellersWhoEarnedMostView>(
      `${this.resourceUrl}/${this.getSellersWhoEarnedMostViewIdentifier(sellersWhoEarnedMostView)}`,
      sellersWhoEarnedMostView,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISellersWhoEarnedMostView>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISellersWhoEarnedMostView[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSellersWhoEarnedMostViewIdentifier(sellersWhoEarnedMostView: Pick<ISellersWhoEarnedMostView, 'id'>): number {
    return sellersWhoEarnedMostView.id;
  }

  compareSellersWhoEarnedMostView(
    o1: Pick<ISellersWhoEarnedMostView, 'id'> | null,
    o2: Pick<ISellersWhoEarnedMostView, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getSellersWhoEarnedMostViewIdentifier(o1) === this.getSellersWhoEarnedMostViewIdentifier(o2) : o1 === o2;
  }

  addSellersWhoEarnedMostViewToCollectionIfMissing<Type extends Pick<ISellersWhoEarnedMostView, 'id'>>(
    sellersWhoEarnedMostViewCollection: Type[],
    ...sellersWhoEarnedMostViewsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sellersWhoEarnedMostViews: Type[] = sellersWhoEarnedMostViewsToCheck.filter(isPresent);
    if (sellersWhoEarnedMostViews.length > 0) {
      const sellersWhoEarnedMostViewCollectionIdentifiers = sellersWhoEarnedMostViewCollection.map(
        sellersWhoEarnedMostViewItem => this.getSellersWhoEarnedMostViewIdentifier(sellersWhoEarnedMostViewItem)!,
      );
      const sellersWhoEarnedMostViewsToAdd = sellersWhoEarnedMostViews.filter(sellersWhoEarnedMostViewItem => {
        const sellersWhoEarnedMostViewIdentifier = this.getSellersWhoEarnedMostViewIdentifier(sellersWhoEarnedMostViewItem);
        if (sellersWhoEarnedMostViewCollectionIdentifiers.includes(sellersWhoEarnedMostViewIdentifier)) {
          return false;
        }
        sellersWhoEarnedMostViewCollectionIdentifiers.push(sellersWhoEarnedMostViewIdentifier);
        return true;
      });
      return [...sellersWhoEarnedMostViewsToAdd, ...sellersWhoEarnedMostViewCollection];
    }
    return sellersWhoEarnedMostViewCollection;
  }
}
