import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISellersWhoSoldMostProductsView, NewSellersWhoSoldMostProductsView } from '../sellers-who-sold-most-products-view.model';

export type PartialUpdateSellersWhoSoldMostProductsView = Partial<ISellersWhoSoldMostProductsView> &
  Pick<ISellersWhoSoldMostProductsView, 'id'>;

export type EntityResponseType = HttpResponse<ISellersWhoSoldMostProductsView>;
export type EntityArrayResponseType = HttpResponse<ISellersWhoSoldMostProductsView[]>;

@Injectable({ providedIn: 'root' })
export class SellersWhoSoldMostProductsViewService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sellers-who-sold-most-products-views');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(sellersWhoSoldMostProductsView: NewSellersWhoSoldMostProductsView): Observable<EntityResponseType> {
    return this.http.post<ISellersWhoSoldMostProductsView>(this.resourceUrl, sellersWhoSoldMostProductsView, { observe: 'response' });
  }

  update(sellersWhoSoldMostProductsView: ISellersWhoSoldMostProductsView): Observable<EntityResponseType> {
    return this.http.put<ISellersWhoSoldMostProductsView>(
      `${this.resourceUrl}/${this.getSellersWhoSoldMostProductsViewIdentifier(sellersWhoSoldMostProductsView)}`,
      sellersWhoSoldMostProductsView,
      { observe: 'response' },
    );
  }

  partialUpdate(sellersWhoSoldMostProductsView: PartialUpdateSellersWhoSoldMostProductsView): Observable<EntityResponseType> {
    return this.http.patch<ISellersWhoSoldMostProductsView>(
      `${this.resourceUrl}/${this.getSellersWhoSoldMostProductsViewIdentifier(sellersWhoSoldMostProductsView)}`,
      sellersWhoSoldMostProductsView,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISellersWhoSoldMostProductsView>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISellersWhoSoldMostProductsView[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSellersWhoSoldMostProductsViewIdentifier(sellersWhoSoldMostProductsView: Pick<ISellersWhoSoldMostProductsView, 'id'>): number {
    return sellersWhoSoldMostProductsView.id;
  }

  compareSellersWhoSoldMostProductsView(
    o1: Pick<ISellersWhoSoldMostProductsView, 'id'> | null,
    o2: Pick<ISellersWhoSoldMostProductsView, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getSellersWhoSoldMostProductsViewIdentifier(o1) === this.getSellersWhoSoldMostProductsViewIdentifier(o2)
      : o1 === o2;
  }

  addSellersWhoSoldMostProductsViewToCollectionIfMissing<Type extends Pick<ISellersWhoSoldMostProductsView, 'id'>>(
    sellersWhoSoldMostProductsViewCollection: Type[],
    ...sellersWhoSoldMostProductsViewsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sellersWhoSoldMostProductsViews: Type[] = sellersWhoSoldMostProductsViewsToCheck.filter(isPresent);
    if (sellersWhoSoldMostProductsViews.length > 0) {
      const sellersWhoSoldMostProductsViewCollectionIdentifiers = sellersWhoSoldMostProductsViewCollection.map(
        sellersWhoSoldMostProductsViewItem => this.getSellersWhoSoldMostProductsViewIdentifier(sellersWhoSoldMostProductsViewItem)!,
      );
      const sellersWhoSoldMostProductsViewsToAdd = sellersWhoSoldMostProductsViews.filter(sellersWhoSoldMostProductsViewItem => {
        const sellersWhoSoldMostProductsViewIdentifier =
          this.getSellersWhoSoldMostProductsViewIdentifier(sellersWhoSoldMostProductsViewItem);
        if (sellersWhoSoldMostProductsViewCollectionIdentifiers.includes(sellersWhoSoldMostProductsViewIdentifier)) {
          return false;
        }
        sellersWhoSoldMostProductsViewCollectionIdentifiers.push(sellersWhoSoldMostProductsViewIdentifier);
        return true;
      });
      return [...sellersWhoSoldMostProductsViewsToAdd, ...sellersWhoSoldMostProductsViewCollection];
    }
    return sellersWhoSoldMostProductsViewCollection;
  }
}
