import { ITopSellingProductsView, NewTopSellingProductsView } from './top-selling-products-view.model';

export const sampleWithRequiredData: ITopSellingProductsView = {
  id: 9452,
};

export const sampleWithPartialData: ITopSellingProductsView = {
  id: 16322,
};

export const sampleWithFullData: ITopSellingProductsView = {
  id: 24909,
  quantity: 31619,
  position: 11390,
};

export const sampleWithNewData: NewTopSellingProductsView = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
