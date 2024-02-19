import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';
import { ICollaborator } from 'app/entities/collaborator/collaborator.model';
import { CollaboratorService } from 'app/entities/collaborator/service/collaborator.service';
import { ISale } from '../sale.model';
import { SaleService } from '../service/sale.service';
import { SaleFormService } from './sale-form.service';

import { SaleUpdateComponent } from './sale-update.component';

describe('Sale Management Update Component', () => {
  let comp: SaleUpdateComponent;
  let fixture: ComponentFixture<SaleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let saleFormService: SaleFormService;
  let saleService: SaleService;
  let clientService: ClientService;
  let collaboratorService: CollaboratorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), SaleUpdateComponent],
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
      .overrideTemplate(SaleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SaleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    saleFormService = TestBed.inject(SaleFormService);
    saleService = TestBed.inject(SaleService);
    clientService = TestBed.inject(ClientService);
    collaboratorService = TestBed.inject(CollaboratorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Client query and add missing value', () => {
      const sale: ISale = { id: 456 };
      const client: IClient = { id: 22602 };
      sale.client = client;

      const clientCollection: IClient[] = [{ id: 27638 }];
      jest.spyOn(clientService, 'query').mockReturnValue(of(new HttpResponse({ body: clientCollection })));
      const additionalClients = [client];
      const expectedCollection: IClient[] = [...additionalClients, ...clientCollection];
      jest.spyOn(clientService, 'addClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sale });
      comp.ngOnInit();

      expect(clientService.query).toHaveBeenCalled();
      expect(clientService.addClientToCollectionIfMissing).toHaveBeenCalledWith(
        clientCollection,
        ...additionalClients.map(expect.objectContaining),
      );
      expect(comp.clientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Collaborator query and add missing value', () => {
      const sale: ISale = { id: 456 };
      const seller: ICollaborator = { id: 9079 };
      sale.seller = seller;

      const collaboratorCollection: ICollaborator[] = [{ id: 18560 }];
      jest.spyOn(collaboratorService, 'query').mockReturnValue(of(new HttpResponse({ body: collaboratorCollection })));
      const additionalCollaborators = [seller];
      const expectedCollection: ICollaborator[] = [...additionalCollaborators, ...collaboratorCollection];
      jest.spyOn(collaboratorService, 'addCollaboratorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ sale });
      comp.ngOnInit();

      expect(collaboratorService.query).toHaveBeenCalled();
      expect(collaboratorService.addCollaboratorToCollectionIfMissing).toHaveBeenCalledWith(
        collaboratorCollection,
        ...additionalCollaborators.map(expect.objectContaining),
      );
      expect(comp.collaboratorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const sale: ISale = { id: 456 };
      const client: IClient = { id: 26465 };
      sale.client = client;
      const seller: ICollaborator = { id: 27816 };
      sale.seller = seller;

      activatedRoute.data = of({ sale });
      comp.ngOnInit();

      expect(comp.clientsSharedCollection).toContain(client);
      expect(comp.collaboratorsSharedCollection).toContain(seller);
      expect(comp.sale).toEqual(sale);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISale>>();
      const sale = { id: 123 };
      jest.spyOn(saleFormService, 'getSale').mockReturnValue(sale);
      jest.spyOn(saleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sale }));
      saveSubject.complete();

      // THEN
      expect(saleFormService.getSale).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(saleService.update).toHaveBeenCalledWith(expect.objectContaining(sale));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISale>>();
      const sale = { id: 123 };
      jest.spyOn(saleFormService, 'getSale').mockReturnValue({ id: null });
      jest.spyOn(saleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sale: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sale }));
      saveSubject.complete();

      // THEN
      expect(saleFormService.getSale).toHaveBeenCalled();
      expect(saleService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISale>>();
      const sale = { id: 123 };
      jest.spyOn(saleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(saleService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareClient', () => {
      it('Should forward to clientService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(clientService, 'compareClient');
        comp.compareClient(entity, entity2);
        expect(clientService.compareClient).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
