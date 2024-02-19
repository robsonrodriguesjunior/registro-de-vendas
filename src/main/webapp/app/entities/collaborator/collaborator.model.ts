import { IPerson } from 'app/entities/person/person.model';
import { ISale } from 'app/entities/sale/sale.model';
import { ISellersWhoEarnedMostView } from 'app/entities/sellers-who-earned-most-view/sellers-who-earned-most-view.model';
import { ISellersWhoSoldMostProductsView } from 'app/entities/sellers-who-sold-most-products-view/sellers-who-sold-most-products-view.model';
import { CollaboratorType } from 'app/entities/enumerations/collaborator-type.model';
import { CollaboratorStatus } from 'app/entities/enumerations/collaborator-status.model';

export interface ICollaborator {
  id: number;
  code?: string | null;
  type?: keyof typeof CollaboratorType | null;
  status?: keyof typeof CollaboratorStatus | null;
  person?: IPerson | null;
  sales?: ISale[] | null;
  sellersWhoEarnedMostView?: ISellersWhoEarnedMostView | null;
  sellersWhoSoldMostProductsView?: ISellersWhoSoldMostProductsView | null;
}

export type NewCollaborator = Omit<ICollaborator, 'id'> & { id: null };
