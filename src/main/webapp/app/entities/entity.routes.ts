import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'person',
    data: { pageTitle: 'registroDeVendasApp.person.home.title' },
    loadChildren: () => import('./person/person.routes'),
  },
  {
    path: 'client',
    data: { pageTitle: 'registroDeVendasApp.client.home.title' },
    loadChildren: () => import('./client/client.routes'),
  },
  {
    path: 'collaborator',
    data: { pageTitle: 'registroDeVendasApp.collaborator.home.title' },
    loadChildren: () => import('./collaborator/collaborator.routes'),
  },
  {
    path: 'category',
    data: { pageTitle: 'registroDeVendasApp.category.home.title' },
    loadChildren: () => import('./category/category.routes'),
  },
  {
    path: 'product',
    data: { pageTitle: 'registroDeVendasApp.product.home.title' },
    loadChildren: () => import('./product/product.routes'),
  },
  {
    path: 'sale',
    data: { pageTitle: 'registroDeVendasApp.sale.home.title' },
    loadChildren: () => import('./sale/sale.routes'),
  },
  {
    path: 'sellers-who-earned-most-view',
    data: { pageTitle: 'registroDeVendasApp.sellersWhoEarnedMostView.home.title' },
    loadChildren: () => import('./sellers-who-earned-most-view/sellers-who-earned-most-view.routes'),
  },
  {
    path: 'sellers-who-sold-most-products-view',
    data: { pageTitle: 'registroDeVendasApp.sellersWhoSoldMostProductsView.home.title' },
    loadChildren: () => import('./sellers-who-sold-most-products-view/sellers-who-sold-most-products-view.routes'),
  },
  {
    path: 'top-selling-products-view',
    data: { pageTitle: 'registroDeVendasApp.topSellingProductsView.home.title' },
    loadChildren: () => import('./top-selling-products-view/top-selling-products-view.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
