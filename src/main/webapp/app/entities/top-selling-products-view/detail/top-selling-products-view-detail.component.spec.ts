import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TopSellingProductsViewDetailComponent } from './top-selling-products-view-detail.component';

describe('TopSellingProductsView Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TopSellingProductsViewDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: TopSellingProductsViewDetailComponent,
              resolve: { topSellingProductsView: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(TopSellingProductsViewDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load topSellingProductsView on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', TopSellingProductsViewDetailComponent);

      // THEN
      expect(instance.topSellingProductsView).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
