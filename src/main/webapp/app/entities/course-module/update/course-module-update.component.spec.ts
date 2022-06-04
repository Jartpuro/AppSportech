import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CourseModuleService } from '../service/course-module.service';
import { ICourseModule, CourseModule } from '../course-module.model';
import { ICourse } from 'app/entities/course/course.model';
import { CourseService } from 'app/entities/course/service/course.service';
import { IModule } from 'app/entities/module/module.model';
import { ModuleService } from 'app/entities/module/service/module.service';

import { CourseModuleUpdateComponent } from './course-module-update.component';

describe('CourseModule Management Update Component', () => {
  let comp: CourseModuleUpdateComponent;
  let fixture: ComponentFixture<CourseModuleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let courseModuleService: CourseModuleService;
  let courseService: CourseService;
  let moduleService: ModuleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CourseModuleUpdateComponent],
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
      .overrideTemplate(CourseModuleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CourseModuleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    courseModuleService = TestBed.inject(CourseModuleService);
    courseService = TestBed.inject(CourseService);
    moduleService = TestBed.inject(ModuleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Course query and add missing value', () => {
      const courseModule: ICourseModule = { id: 456 };
      const course: ICourse = { id: 55954 };
      courseModule.course = course;

      const courseCollection: ICourse[] = [{ id: 49329 }];
      jest.spyOn(courseService, 'query').mockReturnValue(of(new HttpResponse({ body: courseCollection })));
      const additionalCourses = [course];
      const expectedCollection: ICourse[] = [...additionalCourses, ...courseCollection];
      jest.spyOn(courseService, 'addCourseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ courseModule });
      comp.ngOnInit();

      expect(courseService.query).toHaveBeenCalled();
      expect(courseService.addCourseToCollectionIfMissing).toHaveBeenCalledWith(courseCollection, ...additionalCourses);
      expect(comp.coursesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Module query and add missing value', () => {
      const courseModule: ICourseModule = { id: 456 };
      const module: IModule = { id: 89880 };
      courseModule.module = module;

      const moduleCollection: IModule[] = [{ id: 99487 }];
      jest.spyOn(moduleService, 'query').mockReturnValue(of(new HttpResponse({ body: moduleCollection })));
      const additionalModules = [module];
      const expectedCollection: IModule[] = [...additionalModules, ...moduleCollection];
      jest.spyOn(moduleService, 'addModuleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ courseModule });
      comp.ngOnInit();

      expect(moduleService.query).toHaveBeenCalled();
      expect(moduleService.addModuleToCollectionIfMissing).toHaveBeenCalledWith(moduleCollection, ...additionalModules);
      expect(comp.modulesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const courseModule: ICourseModule = { id: 456 };
      const course: ICourse = { id: 312 };
      courseModule.course = course;
      const module: IModule = { id: 15193 };
      courseModule.module = module;

      activatedRoute.data = of({ courseModule });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(courseModule));
      expect(comp.coursesSharedCollection).toContain(course);
      expect(comp.modulesSharedCollection).toContain(module);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CourseModule>>();
      const courseModule = { id: 123 };
      jest.spyOn(courseModuleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courseModule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: courseModule }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(courseModuleService.update).toHaveBeenCalledWith(courseModule);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CourseModule>>();
      const courseModule = new CourseModule();
      jest.spyOn(courseModuleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courseModule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: courseModule }));
      saveSubject.complete();

      // THEN
      expect(courseModuleService.create).toHaveBeenCalledWith(courseModule);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CourseModule>>();
      const courseModule = { id: 123 };
      jest.spyOn(courseModuleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courseModule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(courseModuleService.update).toHaveBeenCalledWith(courseModule);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCourseById', () => {
      it('Should return tracked Course primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCourseById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackModuleById', () => {
      it('Should return tracked Module primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackModuleById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
