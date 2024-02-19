import { IProduct } from 'app/entities/product/product.model';

export interface ICategory {
  id: number;
  code?: string | null;
  name?: string | null;
  products?: IProduct[] | null;
}

export type NewCategory = Omit<ICategory, 'id'> & { id: null };
