import { ISellersWhoSoldMostProductsView, NewSellersWhoSoldMostProductsView } from './sellers-who-sold-most-products-view.model';

export const sampleWithRequiredData: ISellersWhoSoldMostProductsView = {
  id: 32661,
};

export const sampleWithPartialData: ISellersWhoSoldMostProductsView = {
  id: 289,
  quantity: 20648,
};

export const sampleWithFullData: ISellersWhoSoldMostProductsView = {
  id: 27202,
  quantity: 29630,
  position: 14953,
};

export const sampleWithNewData: NewSellersWhoSoldMostProductsView = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
