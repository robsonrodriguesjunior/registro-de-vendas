import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SellersWhoEarnedMostViewDetailComponent } from './sellers-who-earned-most-view-detail.component';

describe('SellersWhoEarnedMostView Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellersWhoEarnedMostViewDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: SellersWhoEarnedMostViewDetailComponent,
              resolve: { sellersWhoEarnedMostView: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SellersWhoEarnedMostViewDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load sellersWhoEarnedMostView on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SellersWhoEarnedMostViewDetailComponent);

      // THEN
      expect(instance.sellersWhoEarnedMostView).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
