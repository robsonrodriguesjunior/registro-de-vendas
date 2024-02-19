import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';
import { CollaboratorService } from '../service/collaborator.service';
import { ICollaborator } from '../collaborator.model';
import { CollaboratorFormService } from './collaborator-form.service';

import { CollaboratorUpdateComponent } from './collaborator-update.component';

describe('Collaborator Management Update Component', () => {
  let comp: CollaboratorUpdateComponent;
  let fixture: ComponentFixture<CollaboratorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let collaboratorFormService: CollaboratorFormService;
  let collaboratorService: CollaboratorService;
  let personService: PersonService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), CollaboratorUpdateComponent],
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
      .overrideTemplate(CollaboratorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CollaboratorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    collaboratorFormService = TestBed.inject(CollaboratorFormService);
    collaboratorService = TestBed.inject(CollaboratorService);
    personService = TestBed.inject(PersonService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call person query and add missing value', () => {
      const collaborator: ICollaborator = { id: 456 };
      const person: IPerson = { id: 24951 };
      collaborator.person = person;

      const personCollection: IPerson[] = [{ id: 20301 }];
      jest.spyOn(personService, 'query').mockReturnValue(of(new HttpResponse({ body: personCollection })));
      const expectedCollection: IPerson[] = [person, ...personCollection];
      jest.spyOn(personService, 'addPersonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ collaborator });
      comp.ngOnInit();

      expect(personService.query).toHaveBeenCalled();
      expect(personService.addPersonToCollectionIfMissing).toHaveBeenCalledWith(personCollection, person);
      expect(comp.peopleCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const collaborator: ICollaborator = { id: 456 };
      const person: IPerson = { id: 18906 };
      collaborator.person = person;

      activatedRoute.data = of({ collaborator });
      comp.ngOnInit();

      expect(comp.peopleCollection).toContain(person);
      expect(comp.collaborator).toEqual(collaborator);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICollaborator>>();
      const collaborator = { id: 123 };
      jest.spyOn(collaboratorFormService, 'getCollaborator').mockReturnValue(collaborator);
      jest.spyOn(collaboratorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ collaborator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: collaborator }));
      saveSubject.complete();

      // THEN
      expect(collaboratorFormService.getCollaborator).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(collaboratorService.update).toHaveBeenCalledWith(expect.objectContaining(collaborator));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICollaborator>>();
      const collaborator = { id: 123 };
      jest.spyOn(collaboratorFormService, 'getCollaborator').mockReturnValue({ id: null });
      jest.spyOn(collaboratorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ collaborator: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: collaborator }));
      saveSubject.complete();

      // THEN
      expect(collaboratorFormService.getCollaborator).toHaveBeenCalled();
      expect(collaboratorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICollaborator>>();
      const collaborator = { id: 123 };
      jest.spyOn(collaboratorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ collaborator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(collaboratorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePerson', () => {
      it('Should forward to personService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(personService, 'comparePerson');
        comp.comparePerson(entity, entity2);
        expect(personService.comparePerson).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
