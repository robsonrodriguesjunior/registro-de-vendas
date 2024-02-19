import { ICollaborator, NewCollaborator } from './collaborator.model';

export const sampleWithRequiredData: ICollaborator = {
  id: 5821,
  code: 'drat boohoo after',
  type: 'MANAGER',
  status: 'INACTIVE',
};

export const sampleWithPartialData: ICollaborator = {
  id: 20509,
  code: 'tremendous joshingly',
  type: 'SELLER',
  status: 'ACTIVE',
};

export const sampleWithFullData: ICollaborator = {
  id: 15607,
  code: 'cluster knobby content',
  type: 'SELLER',
  status: 'ACTIVE',
};

export const sampleWithNewData: NewCollaborator = {
  code: 'ack wherever',
  type: 'MANAGER',
  status: 'INACTIVE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
