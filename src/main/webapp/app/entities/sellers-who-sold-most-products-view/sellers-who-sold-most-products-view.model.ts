import { ICollaborator } from 'app/entities/collaborator/collaborator.model';

export interface ISellersWhoSoldMostProductsView {
  id: number;
  quantity?: number | null;
  position?: number | null;
  seller?: ICollaborator | null;
}

export type NewSellersWhoSoldMostProductsView = Omit<ISellersWhoSoldMostProductsView, 'id'> & { id: null };
