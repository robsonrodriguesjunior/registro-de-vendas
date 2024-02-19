import { ICategory } from 'app/entities/category/category.model';
import { ISale } from 'app/entities/sale/sale.model';
import { ITopSellingProductsView } from 'app/entities/top-selling-products-view/top-selling-products-view.model';

export interface IProduct {
  id: number;
  code?: string | null;
  name?: string | null;
  category?: ICategory | null;
  sales?: ISale[] | null;
  topSellingProductsView?: ITopSellingProductsView | null;
}

export type NewProduct = Omit<IProduct, 'id'> & { id: null };
