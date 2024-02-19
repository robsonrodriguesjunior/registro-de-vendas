import dayjs from 'dayjs/esm';

import { IPerson, NewPerson } from './person.model';

export const sampleWithRequiredData: IPerson = {
  id: 29978,
  firstName: 'Mariana',
  secondName: 'pfft jealously',
  birthday: dayjs('2024-02-18'),
};

export const sampleWithPartialData: IPerson = {
  id: 26871,
  firstName: 'Washington',
  secondName: 'frayed bah',
  birthday: dayjs('2024-02-18'),
};

export const sampleWithFullData: IPerson = {
  id: 15216,
  firstName: 'Júlio César',
  secondName: 'parchment',
  birthday: dayjs('2024-02-18'),
};

export const sampleWithNewData: NewPerson = {
  firstName: 'Ladislau',
  secondName: 'progression',
  birthday: dayjs('2024-02-18'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
