import { ICollaborator } from 'app/entities/collaborator/collaborator.model';

export interface ISellersWhoEarnedMostView {
  id: number;
  value?: number | null;
  position?: number | null;
  seller?: ICollaborator | null;
}

export type NewSellersWhoEarnedMostView = Omit<ISellersWhoEarnedMostView, 'id'> & { id: null };
