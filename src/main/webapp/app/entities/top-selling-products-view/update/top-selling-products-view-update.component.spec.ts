import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { TopSellingProductsViewService } from '../service/top-selling-products-view.service';
import { ITopSellingProductsView } from '../top-selling-products-view.model';
import { TopSellingProductsViewFormService } from './top-selling-products-view-form.service';

import { TopSellingProductsViewUpdateComponent } from './top-selling-products-view-update.component';

describe('TopSellingProductsView Management Update Component', () => {
  let comp: TopSellingProductsViewUpdateComponent;
  let fixture: ComponentFixture<TopSellingProductsViewUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let topSellingProductsViewFormService: TopSellingProductsViewFormService;
  let topSellingProductsViewService: TopSellingProductsViewService;
  let productService: ProductService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), TopSellingProductsViewUpdateComponent],
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
      .overrideTemplate(TopSellingProductsViewUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TopSellingProductsViewUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    topSellingProductsViewFormService = TestBed.inject(TopSellingProductsViewFormService);
    topSellingProductsViewService = TestBed.inject(TopSellingProductsViewService);
    productService = TestBed.inject(ProductService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call product query and add missing value', () => {
      const topSellingProductsView: ITopSellingProductsView = { id: 456 };
      const product: IProduct = { id: 1906 };
      topSellingProductsView.product = product;

      const productCollection: IProduct[] = [{ id: 16544 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const expectedCollection: IProduct[] = [product, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ topSellingProductsView });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(productCollection, product);
      expect(comp.productsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const topSellingProductsView: ITopSellingProductsView = { id: 456 };
      const product: IProduct = { id: 32553 };
      topSellingProductsView.product = product;

      activatedRoute.data = of({ topSellingProductsView });
      comp.ngOnInit();

      expect(comp.productsCollection).toContain(product);
      expect(comp.topSellingProductsView).toEqual(topSellingProductsView);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITopSellingProductsView>>();
      const topSellingProductsView = { id: 123 };
      jest.spyOn(topSellingProductsViewFormService, 'getTopSellingProductsView').mockReturnValue(topSellingProductsView);
      jest.spyOn(topSellingProductsViewService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ topSellingProductsView });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: topSellingProductsView }));
      saveSubject.complete();

      // THEN
      expect(topSellingProductsViewFormService.getTopSellingProductsView).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(topSellingProductsViewService.update).toHaveBeenCalledWith(expect.objectContaining(topSellingProductsView));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITopSellingProductsView>>();
      const topSellingProductsView = { id: 123 };
      jest.spyOn(topSellingProductsViewFormService, 'getTopSellingProductsView').mockReturnValue({ id: null });
      jest.spyOn(topSellingProductsViewService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ topSellingProductsView: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: topSellingProductsView }));
      saveSubject.complete();

      // THEN
      expect(topSellingProductsViewFormService.getTopSellingProductsView).toHaveBeenCalled();
      expect(topSellingProductsViewService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITopSellingProductsView>>();
      const topSellingProductsView = { id: 123 };
      jest.spyOn(topSellingProductsViewService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ topSellingProductsView });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(topSellingProductsViewService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProduct', () => {
      it('Should forward to productService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(productService, 'compareProduct');
        comp.compareProduct(entity, entity2);
        expect(productService.compareProduct).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
