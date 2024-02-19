import { IPerson } from 'app/entities/person/person.model';
import { ISale } from 'app/entities/sale/sale.model';

export interface IClient {
  id: number;
  code?: string | null;
  person?: IPerson | null;
  buys?: ISale[] | null;
}

export type NewClient = Omit<IClient, 'id'> & { id: null };
