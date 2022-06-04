import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CourseService } from '../service/course.service';
import { ICourse, Course } from '../course.model';
import { ICourseStatus } from 'app/entities/course-status/course-status.model';
import { CourseStatusService } from 'app/entities/course-status/service/course-status.service';
import { ITrainingProgram } from 'app/entities/training-program/training-program.model';
import { TrainingProgramService } from 'app/entities/training-program/service/training-program.service';

import { CourseUpdateComponent } from './course-update.component';

describe('Course Management Update Component', () => {
  let comp: CourseUpdateComponent;
  let fixture: ComponentFixture<CourseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let courseService: CourseService;
  let courseStatusService: CourseStatusService;
  let trainingProgramService: TrainingProgramService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CourseUpdateComponent],
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
      .overrideTemplate(CourseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CourseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    courseService = TestBed.inject(CourseService);
    courseStatusService = TestBed.inject(CourseStatusService);
    trainingProgramService = TestBed.inject(TrainingProgramService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CourseStatus query and add missing value', () => {
      const course: ICourse = { id: 456 };
      const courseStatus: ICourseStatus = { id: 92974 };
      course.courseStatus = courseStatus;

      const courseStatusCollection: ICourseStatus[] = [{ id: 58389 }];
      jest.spyOn(courseStatusService, 'query').mockReturnValue(of(new HttpResponse({ body: courseStatusCollection })));
      const additionalCourseStatuses = [courseStatus];
      const expectedCollection: ICourseStatus[] = [...additionalCourseStatuses, ...courseStatusCollection];
      jest.spyOn(courseStatusService, 'addCourseStatusToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ course });
      comp.ngOnInit();

      expect(courseStatusService.query).toHaveBeenCalled();
      expect(courseStatusService.addCourseStatusToCollectionIfMissing).toHaveBeenCalledWith(
        courseStatusCollection,
        ...additionalCourseStatuses
      );
      expect(comp.courseStatusesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TrainingProgram query and add missing value', () => {
      const course: ICourse = { id: 456 };
      const trainingProgram: ITrainingProgram = { id: 74930 };
      course.trainingProgram = trainingProgram;

      const trainingProgramCollection: ITrainingProgram[] = [{ id: 74025 }];
      jest.spyOn(trainingProgramService, 'query').mockReturnValue(of(new HttpResponse({ body: trainingProgramCollection })));
      const additionalTrainingPrograms = [trainingProgram];
      const expectedCollection: ITrainingProgram[] = [...additionalTrainingPrograms, ...trainingProgramCollection];
      jest.spyOn(trainingProgramService, 'addTrainingProgramToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ course });
      comp.ngOnInit();

      expect(trainingProgramService.query).toHaveBeenCalled();
      expect(trainingProgramService.addTrainingProgramToCollectionIfMissing).toHaveBeenCalledWith(
        trainingProgramCollection,
        ...additionalTrainingPrograms
      );
      expect(comp.trainingProgramsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const course: ICourse = { id: 456 };
      const courseStatus: ICourseStatus = { id: 71075 };
      course.courseStatus = courseStatus;
      const trainingProgram: ITrainingProgram = { id: 95839 };
      course.trainingProgram = trainingProgram;

      activatedRoute.data = of({ course });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(course));
      expect(comp.courseStatusesSharedCollection).toContain(courseStatus);
      expect(comp.trainingProgramsSharedCollection).toContain(trainingProgram);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Course>>();
      const course = { id: 123 };
      jest.spyOn(courseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ course });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: course }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(courseService.update).toHaveBeenCalledWith(course);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Course>>();
      const course = new Course();
      jest.spyOn(courseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ course });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: course }));
      saveSubject.complete();

      // THEN
      expect(courseService.create).toHaveBeenCalledWith(course);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Course>>();
      const course = { id: 123 };
      jest.spyOn(courseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ course });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(courseService.update).toHaveBeenCalledWith(course);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCourseStatusById', () => {
      it('Should return tracked CourseStatus primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCourseStatusById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTrainingProgramById', () => {
      it('Should return tracked TrainingProgram primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTrainingProgramById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
