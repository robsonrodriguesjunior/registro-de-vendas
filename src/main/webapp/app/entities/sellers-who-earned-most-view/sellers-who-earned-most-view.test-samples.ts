import { ISellersWhoEarnedMostView, NewSellersWhoEarnedMostView } from './sellers-who-earned-most-view.model';

export const sampleWithRequiredData: ISellersWhoEarnedMostView = {
  id: 23185,
};

export const sampleWithPartialData: ISellersWhoEarnedMostView = {
  id: 28053,
  position: 17816,
};

export const sampleWithFullData: ISellersWhoEarnedMostView = {
  id: 17677,
  value: 23640.55,
  position: 13622,
};

export const sampleWithNewData: NewSellersWhoEarnedMostView = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
