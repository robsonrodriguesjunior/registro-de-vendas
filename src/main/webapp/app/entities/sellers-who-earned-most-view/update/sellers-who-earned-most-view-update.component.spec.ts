import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICollaborator } from 'app/entities/collaborator/collaborator.model';
import { CollaboratorService } from 'app/entities/collaborator/service/collaborator.service';
import { SellersWhoEarnedMostViewService } from '../service/sellers-who-earned-most-view.service';
import { ISellersWhoEarnedMostView } from '../sellers-who-earned-most-view.model';
import { SellersWhoEarnedMostViewFormService } from './sellers-who-earned-most-view-form.service';

import { SellersWhoEarnedMostViewUpdateComponent } from './sellers-who-earned-most-view-update.component';

describe('SellersWhoEarnedMostView Management Update Component', () => {
  let comp: SellersWhoEarnedMostViewUpdateComponent;
  let fixture: ComponentFixture<SellersWhoEarnedMostViewUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sellersWhoEarnedMostViewFormService: SellersWhoEarnedMostViewFormService;
  let sellersWhoEarnedMostViewService: SellersWhoEarnedMostViewService;
  let collaboratorService: CollaboratorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), SellersWhoEarnedMostViewUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SellersWhoEarnedMostViewUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SellersWhoEarnedMostViewUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sellersWhoEarnedMostViewFormService = TestBed.inject(SellersWhoEarnedMostViewFormService);
    sellersWhoEarnedMostViewService = TestBed.inject(SellersWhoEarnedMostViewService);
    collaboratorService = TestBed.inject(CollaboratorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call seller query and add missing value', () => {
      const sellersWhoEarnedMostView: ISellersWhoEarnedMostView = { id: 456 };
      const seller: ICollaborator = { id: 28669 };
      sellersWhoEarnedMostView.seller = seller;

      const sellerCollection: ICollaborator[] = [{ id: 32131 }];
      jest.spyOn(collaboratorService, 'query').mockReturnValue(of(new HttpResponse({ body: sellerCollection })));
      const expectedCollection: ICollaborator[] = [seller, ...sellerCollection];
      jest.spyOn(collaboratorService, 'addCollaboratorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sellersWhoEarnedMostView });
      comp.ngOnInit();

      expect(collaboratorService.query).toHaveBeenCalled();
      expect(collaboratorService.addCollaboratorToCollectionIfMissing).toHaveBeenCalledWith(sellerCollection, seller);
      expect(comp.sellersCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const sellersWhoEarnedMostView: ISellersWhoEarnedMostView = { id: 456 };
      const seller: ICollaborator = { id: 13342 };
      sellersWhoEarnedMostView.seller = seller;

      activatedRoute.data = of({ sellersWhoEarnedMostView });
      comp.ngOnInit();

      expect(comp.sellersCollection).toContain(seller);
      expect(comp.sellersWhoEarnedMostView).toEqual(sellersWhoEarnedMostView);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISellersWhoEarnedMostView>>();
      const sellersWhoEarnedMostView = { id: 123 };
      jest.spyOn(sellersWhoEarnedMostViewFormService, 'getSellersWhoEarnedMostView').mockReturnValue(sellersWhoEarnedMostView);
      jest.spyOn(sellersWhoEarnedMostViewService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sellersWhoEarnedMostView });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sellersWhoEarnedMostView }));
      saveSubject.complete();

      // THEN
      expect(sellersWhoEarnedMostViewFormService.getSellersWhoEarnedMostView).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sellersWhoEarnedMostViewService.update).toHaveBeenCalledWith(expect.objectContaining(sellersWhoEarnedMostView));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISellersWhoEarnedMostView>>();
      const sellersWhoEarnedMostView = { id: 123 };
      jest.spyOn(sellersWhoEarnedMostViewFormService, 'getSellersWhoEarnedMostView').mockReturnValue({ id: null });
      jest.spyOn(sellersWhoEarnedMostViewService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sellersWhoEarnedMostView: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sellersWhoEarnedMostView }));
      saveSubject.complete();

      // THEN
      expect(sellersWhoEarnedMostViewFormService.getSellersWhoEarnedMostView).toHaveBeenCalled();
      expect(sellersWhoEarnedMostViewService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISellersWhoEarnedMostView>>();
      const sellersWhoEarnedMostView = { id: 123 };
      jest.spyOn(sellersWhoEarnedMostViewService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sellersWhoEarnedMostView });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sellersWhoEarnedMostViewService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCollaborator', () => {
      it('Should forward to collaboratorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(collaboratorService, 'compareCollaborator');
        comp.compareCollaborator(entity, entity2);
        expect(collaboratorService.compareCollaborator).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
