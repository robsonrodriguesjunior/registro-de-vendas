import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICollaborator } from 'app/entities/collaborator/collaborator.model';
import { CollaboratorService } from 'app/entities/collaborator/service/collaborator.service';
import { SellersWhoSoldMostProductsViewService } from '../service/sellers-who-sold-most-products-view.service';
import { ISellersWhoSoldMostProductsView } from '../sellers-who-sold-most-products-view.model';
import { SellersWhoSoldMostProductsViewFormService } from './sellers-who-sold-most-products-view-form.service';

import { SellersWhoSoldMostProductsViewUpdateComponent } from './sellers-who-sold-most-products-view-update.component';

describe('SellersWhoSoldMostProductsView Management Update Component', () => {
  let comp: SellersWhoSoldMostProductsViewUpdateComponent;
  let fixture: ComponentFixture<SellersWhoSoldMostProductsViewUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sellersWhoSoldMostProductsViewFormService: SellersWhoSoldMostProductsViewFormService;
  let sellersWhoSoldMostProductsViewService: SellersWhoSoldMostProductsViewService;
  let collaboratorService: CollaboratorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), SellersWhoSoldMostProductsViewUpdateComponent],
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
      .overrideTemplate(SellersWhoSoldMostProductsViewUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SellersWhoSoldMostProductsViewUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sellersWhoSoldMostProductsViewFormService = TestBed.inject(SellersWhoSoldMostProductsViewFormService);
    sellersWhoSoldMostProductsViewService = TestBed.inject(SellersWhoSoldMostProductsViewService);
    collaboratorService = TestBed.inject(CollaboratorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call seller query and add missing value', () => {
      const sellersWhoSoldMostProductsView: ISellersWhoSoldMostProductsView = { id: 456 };
      const seller: ICollaborator = { id: 781 };
      sellersWhoSoldMostProductsView.seller = seller;

      const sellerCollection: ICollaborator[] = [{ id: 1604 }];
      jest.spyOn(collaboratorService, 'query').mockReturnValue(of(new HttpResponse({ body: sellerCollection })));
      const expectedCollection: ICollaborator[] = [seller, ...sellerCollection];
      jest.spyOn(collaboratorService, 'addCollaboratorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sellersWhoSoldMostProductsView });
      comp.ngOnInit();

      expect(collaboratorService.query).toHaveBeenCalled();
      expect(collaboratorService.addCollaboratorToCollectionIfMissing).toHaveBeenCalledWith(sellerCollection, seller);
      expect(comp.sellersCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const sellersWhoSoldMostProductsView: ISellersWhoSoldMostProductsView = { id: 456 };
      const seller: ICollaborator = { id: 20601 };
      sellersWhoSoldMostProductsView.seller = seller;

      activatedRoute.data = of({ sellersWhoSoldMostProductsView });
      comp.ngOnInit();

      expect(comp.sellersCollection).toContain(seller);
      expect(comp.sellersWhoSoldMostProductsView).toEqual(sellersWhoSoldMostProductsView);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISellersWhoSoldMostProductsView>>();
      const sellersWhoSoldMostProductsView = { id: 123 };
      jest
        .spyOn(sellersWhoSoldMostProductsViewFormService, 'getSellersWhoSoldMostProductsView')
        .mockReturnValue(sellersWhoSoldMostProductsView);
      jest.spyOn(sellersWhoSoldMostProductsViewService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sellersWhoSoldMostProductsView });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sellersWhoSoldMostProductsView }));
      saveSubject.complete();

      // THEN
      expect(sellersWhoSoldMostProductsViewFormService.getSellersWhoSoldMostProductsView).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sellersWhoSoldMostProductsViewService.update).toHaveBeenCalledWith(expect.objectContaining(sellersWhoSoldMostProductsView));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISellersWhoSoldMostProductsView>>();
      const sellersWhoSoldMostProductsView = { id: 123 };
      jest.spyOn(sellersWhoSoldMostProductsViewFormService, 'getSellersWhoSoldMostProductsView').mockReturnValue({ id: null });
      jest.spyOn(sellersWhoSoldMostProductsViewService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sellersWhoSoldMostProductsView: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sellersWhoSoldMostProductsView }));
      saveSubject.complete();

      // THEN
      expect(sellersWhoSoldMostProductsViewFormService.getSellersWhoSoldMostProductsView).toHaveBeenCalled();
      expect(sellersWhoSoldMostProductsViewService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISellersWhoSoldMostProductsView>>();
      const sellersWhoSoldMostProductsView = { id: 123 };
      jest.spyOn(sellersWhoSoldMostProductsViewService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sellersWhoSoldMostProductsView });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sellersWhoSoldMostProductsViewService.update).toHaveBeenCalled();
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
