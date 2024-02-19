import { IProduct, NewProduct } from './product.model';

export const sampleWithRequiredData: IProduct = {
  id: 26122,
  code: 'broadcast reward',
  name: 'for readily scarf',
};

export const sampleWithPartialData: IProduct = {
  id: 12099,
  code: 'cartload oof only',
  name: 'against sweetly jaunty',
};

export const sampleWithFullData: IProduct = {
  id: 29501,
  code: 'relative',
  name: 'amid wiggly if',
};

export const sampleWithNewData: NewProduct = {
  code: 'clack',
  name: 'generally but',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
