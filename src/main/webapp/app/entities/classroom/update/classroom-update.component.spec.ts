import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ClassroomService } from '../service/classroom.service';
import { IClassroom, Classroom } from '../classroom.model';
import { IClassroomType } from 'app/entities/classroom-type/classroom-type.model';
import { ClassroomTypeService } from 'app/entities/classroom-type/service/classroom-type.service';
import { ICampus } from 'app/entities/campus/campus.model';
import { CampusService } from 'app/entities/campus/service/campus.service';

import { ClassroomUpdateComponent } from './classroom-update.component';

describe('Classroom Management Update Component', () => {
  let comp: ClassroomUpdateComponent;
  let fixture: ComponentFixture<ClassroomUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let classroomService: ClassroomService;
  let classroomTypeService: ClassroomTypeService;
  let campusService: CampusService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ClassroomUpdateComponent],
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
      .overrideTemplate(ClassroomUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ClassroomUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    classroomService = TestBed.inject(ClassroomService);
    classroomTypeService = TestBed.inject(ClassroomTypeService);
    campusService = TestBed.inject(CampusService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ClassroomType query and add missing value', () => {
      const classroom: IClassroom = { id: 456 };
      const classroomType: IClassroomType = { id: 51456 };
      classroom.classroomType = classroomType;

      const classroomTypeCollection: IClassroomType[] = [{ id: 14769 }];
      jest.spyOn(classroomTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: classroomTypeCollection })));
      const additionalClassroomTypes = [classroomType];
      const expectedCollection: IClassroomType[] = [...additionalClassroomTypes, ...classroomTypeCollection];
      jest.spyOn(classroomTypeService, 'addClassroomTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ classroom });
      comp.ngOnInit();

      expect(classroomTypeService.query).toHaveBeenCalled();
      expect(classroomTypeService.addClassroomTypeToCollectionIfMissing).toHaveBeenCalledWith(
        classroomTypeCollection,
        ...additionalClassroomTypes
      );
      expect(comp.classroomTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Campus query and add missing value', () => {
      const classroom: IClassroom = { id: 456 };
      const campus: ICampus = { id: 18515 };
      classroom.campus = campus;

      const campusCollection: ICampus[] = [{ id: 29706 }];
      jest.spyOn(campusService, 'query').mockReturnValue(of(new HttpResponse({ body: campusCollection })));
      const additionalCampuses = [campus];
      const expectedCollection: ICampus[] = [...additionalCampuses, ...campusCollection];
      jest.spyOn(campusService, 'addCampusToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ classroom });
      comp.ngOnInit();

      expect(campusService.query).toHaveBeenCalled();
      expect(campusService.addCampusToCollectionIfMissing).toHaveBeenCalledWith(campusCollection, ...additionalCampuses);
      expect(comp.campusesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const classroom: IClassroom = { id: 456 };
      const classroomType: IClassroomType = { id: 17897 };
      classroom.classroomType = classroomType;
      const campus: ICampus = { id: 30045 };
      classroom.campus = campus;

      activatedRoute.data = of({ classroom });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(classroom));
      expect(comp.classroomTypesSharedCollection).toContain(classroomType);
      expect(comp.campusesSharedCollection).toContain(campus);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Classroom>>();
      const classroom = { id: 123 };
      jest.spyOn(classroomService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ classroom });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: classroom }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(classroomService.update).toHaveBeenCalledWith(classroom);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Classroom>>();
      const classroom = new Classroom();
      jest.spyOn(classroomService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ classroom });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: classroom }));
      saveSubject.complete();

      // THEN
      expect(classroomService.create).toHaveBeenCalledWith(classroom);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Classroom>>();
      const classroom = { id: 123 };
      jest.spyOn(classroomService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ classroom });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(classroomService.update).toHaveBeenCalledWith(classroom);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackClassroomTypeById', () => {
      it('Should return tracked ClassroomType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackClassroomTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCampusById', () => {
      it('Should return tracked Campus primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCampusById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
