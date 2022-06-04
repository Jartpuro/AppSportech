import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ClassroomTypeService } from '../service/classroom-type.service';
import { IClassroomType, ClassroomType } from '../classroom-type.model';

import { ClassroomTypeUpdateComponent } from './classroom-type-update.component';

describe('ClassroomType Management Update Component', () => {
  let comp: ClassroomTypeUpdateComponent;
  let fixture: ComponentFixture<ClassroomTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let classroomTypeService: ClassroomTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ClassroomTypeUpdateComponent],
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
      .overrideTemplate(ClassroomTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ClassroomTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    classroomTypeService = TestBed.inject(ClassroomTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const classroomType: IClassroomType = { id: 456 };

      activatedRoute.data = of({ classroomType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(classroomType));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ClassroomType>>();
      const classroomType = { id: 123 };
      jest.spyOn(classroomTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ classroomType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: classroomType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(classroomTypeService.update).toHaveBeenCalledWith(classroomType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ClassroomType>>();
      const classroomType = new ClassroomType();
      jest.spyOn(classroomTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ classroomType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: classroomType }));
      saveSubject.complete();

      // THEN
      expect(classroomTypeService.create).toHaveBeenCalledWith(classroomType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ClassroomType>>();
      const classroomType = { id: 123 };
      jest.spyOn(classroomTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ classroomType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(classroomTypeService.update).toHaveBeenCalledWith(classroomType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
