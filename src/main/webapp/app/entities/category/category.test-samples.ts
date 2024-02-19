import { ICategory, NewCategory } from './category.model';

export const sampleWithRequiredData: ICategory = {
  id: 1847,
  code: 'magnificent until',
  name: 'upon boo dispatch',
};

export const sampleWithPartialData: ICategory = {
  id: 416,
  code: 'pomelo',
  name: 'among normal wooden',
};

export const sampleWithFullData: ICategory = {
  id: 31247,
  code: 'extremely whether',
  name: 'quintessential fixture',
};

export const sampleWithNewData: NewCategory = {
  code: 'goggle',
  name: 'headquarters if blah',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
