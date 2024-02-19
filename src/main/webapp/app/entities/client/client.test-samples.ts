import { IClient, NewClient } from './client.model';

export const sampleWithRequiredData: IClient = {
  id: 9423,
  code: 'rotation whereas dresser',
};

export const sampleWithPartialData: IClient = {
  id: 8055,
  code: 'good',
};

export const sampleWithFullData: IClient = {
  id: 2206,
  code: 'owlishly',
};

export const sampleWithNewData: NewClient = {
  code: 'pricey',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
