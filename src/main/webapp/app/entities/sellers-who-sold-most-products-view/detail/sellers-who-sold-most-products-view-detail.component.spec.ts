import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SellersWhoSoldMostProductsViewDetailComponent } from './sellers-who-sold-most-products-view-detail.component';

describe('SellersWhoSoldMostProductsView Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellersWhoSoldMostProductsViewDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: SellersWhoSoldMostProductsViewDetailComponent,
              resolve: { sellersWhoSoldMostProductsView: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SellersWhoSoldMostProductsViewDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load sellersWhoSoldMostProductsView on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SellersWhoSoldMostProductsViewDetailComponent);

      // THEN
      expect(instance.sellersWhoSoldMostProductsView).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
