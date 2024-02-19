import dayjs from 'dayjs/esm';
import { IClient } from 'app/entities/client/client.model';
import { ICollaborator } from 'app/entities/collaborator/collaborator.model';
import { IProduct } from 'app/entities/product/product.model';

export interface ISale {
  id: number;
  date?: dayjs.Dayjs | null;
  client?: IClient | null;
  seller?: ICollaborator | null;
  products?: IProduct[] | null;
}

export type NewSale = Omit<ISale, 'id'> & { id: null };
