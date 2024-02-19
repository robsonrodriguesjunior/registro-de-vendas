import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CollaboratorDetailComponent } from './collaborator-detail.component';

describe('Collaborator Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CollaboratorDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CollaboratorDetailComponent,
              resolve: { collaborator: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CollaboratorDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load collaborator on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CollaboratorDetailComponent);

      // THEN
      expect(instance.collaborator).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
