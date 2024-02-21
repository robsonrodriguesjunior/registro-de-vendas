import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SellersWhoSoldMostProductsViewService } from '../service/sellers-who-sold-most-products-view.service';

import { SellersWhoSoldMostProductsViewComponent } from './sellers-who-sold-most-products-view.component';
import SpyInstance = jest.SpyInstance;

describe('SellersWhoSoldMostProductsView Management Component', () => {
  let comp: SellersWhoSoldMostProductsViewComponent;
  let fixture: ComponentFixture<SellersWhoSoldMostProductsViewComponent>;
  let service: SellersWhoSoldMostProductsViewService;
  let routerNavigateSpy: SpyInstance<Promise<boolean>>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([
          { path: 'sellers-who-sold-most-products-view', component: SellersWhoSoldMostProductsViewComponent },
        ]),
        HttpClientTestingModule,
        SellersWhoSoldMostProductsViewComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
                'filter[someId.in]': 'dc4279ea-cfb9-11ec-9d64-0242ac120002',
              }),
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(SellersWhoSoldMostProductsViewComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SellersWhoSoldMostProductsViewComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SellersWhoSoldMostProductsViewService);
    routerNavigateSpy = jest.spyOn(comp.router, 'navigate');

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        }),
      ),
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.sellersWhoSoldMostProductsViews?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to sellersWhoSoldMostProductsViewService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getSellersWhoSoldMostProductsViewIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getSellersWhoSoldMostProductsViewIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });

  it('should load a page', () => {
    // WHEN
    comp.navigateToPage(1);

    // THEN
    expect(routerNavigateSpy).toHaveBeenCalled();
  });

  it('should calculate the sort attribute for an id', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ sort: ['id,desc'] }));
  });

  it('should calculate the sort attribute for a non-id attribute', () => {
    // GIVEN
    comp.predicate = 'name';

    // WHEN
    comp.navigateToWithComponentValues();

    // THEN
    expect(routerNavigateSpy).toHaveBeenLastCalledWith(
      expect.anything(),
      expect.objectContaining({
        queryParams: expect.objectContaining({
          sort: ['name,asc'],
        }),
      }),
    );
  });

  it('should calculate the filter attribute', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenLastCalledWith(expect.objectContaining({ 'someId.in': ['dc4279ea-cfb9-11ec-9d64-0242ac120002'] }));
  });
});
