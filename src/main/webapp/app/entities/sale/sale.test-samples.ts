import dayjs from 'dayjs/esm';

import { ISale, NewSale } from './sale.model';

export const sampleWithRequiredData: ISale = {
  id: 20780,
  date: dayjs('2024-02-18T02:54'),
};

export const sampleWithPartialData: ISale = {
  id: 925,
  date: dayjs('2024-02-18T05:44'),
};

export const sampleWithFullData: ISale = {
  id: 5764,
  date: dayjs('2024-02-18T13:31'),
};

export const sampleWithNewData: NewSale = {
  date: dayjs('2024-02-18T02:28'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
