import { IProduct } from 'app/entities/product/product.model';

export interface ITopSellingProductsView {
  id: number;
  quantity?: number | null;
  position?: number | null;
  product?: IProduct | null;
}

export type NewTopSellingProductsView = Omit<ITopSellingProductsView, 'id'> & { id: null };
