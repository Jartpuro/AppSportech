import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CourseStatusService } from '../service/course-status.service';
import { ICourseStatus, CourseStatus } from '../course-status.model';

import { CourseStatusUpdateComponent } from './course-status-update.component';

describe('CourseStatus Management Update Component', () => {
  let comp: CourseStatusUpdateComponent;
  let fixture: ComponentFixture<CourseStatusUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let courseStatusService: CourseStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CourseStatusUpdateComponent],
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
      .overrideTemplate(CourseStatusUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CourseStatusUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    courseStatusService = TestBed.inject(CourseStatusService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const courseStatus: ICourseStatus = { id: 456 };

      activatedRoute.data = of({ courseStatus });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(courseStatus));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CourseStatus>>();
      const courseStatus = { id: 123 };
      jest.spyOn(courseStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courseStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: courseStatus }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(courseStatusService.update).toHaveBeenCalledWith(courseStatus);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CourseStatus>>();
      const courseStatus = new CourseStatus();
      jest.spyOn(courseStatusService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courseStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: courseStatus }));
      saveSubject.complete();

      // THEN
      expect(courseStatusService.create).toHaveBeenCalledWith(courseStatus);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CourseStatus>>();
      const courseStatus = { id: 123 };
      jest.spyOn(courseStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ courseStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(courseStatusService.update).toHaveBeenCalledWith(courseStatus);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
