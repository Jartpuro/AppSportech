import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TraineeService } from '../service/trainee.service';
import { ITrainee, Trainee } from '../trainee.model';
import { ICustomer } from 'app/entities/customer/customer.model';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { ITrainingStatus } from 'app/entities/training-status/training-status.model';
import { TrainingStatusService } from 'app/entities/training-status/service/training-status.service';
import { ICourse } from 'app/entities/course/course.model';
import { CourseService } from 'app/entities/course/service/course.service';

import { TraineeUpdateComponent } from './trainee-update.component';

describe('Trainee Management Update Component', () => {
  let comp: TraineeUpdateComponent;
  let fixture: ComponentFixture<TraineeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let traineeService: TraineeService;
  let customerService: CustomerService;
  let trainingStatusService: TrainingStatusService;
  let courseService: CourseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TraineeUpdateComponent],
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
      .overrideTemplate(TraineeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TraineeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    traineeService = TestBed.inject(TraineeService);
    customerService = TestBed.inject(CustomerService);
    trainingStatusService = TestBed.inject(TrainingStatusService);
    courseService = TestBed.inject(CourseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Customer query and add missing value', () => {
      const trainee: ITrainee = { id: 456 };
      const customer: ICustomer = { id: 41122 };
      trainee.customer = customer;

      const customerCollection: ICustomer[] = [{ id: 27529 }];
      jest.spyOn(customerService, 'query').mockReturnValue(of(new HttpResponse({ body: customerCollection })));
      const additionalCustomers = [customer];
      const expectedCollection: ICustomer[] = [...additionalCustomers, ...customerCollection];
      jest.spyOn(customerService, 'addCustomerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ trainee });
      comp.ngOnInit();

      expect(customerService.query).toHaveBeenCalled();
      expect(customerService.addCustomerToCollectionIfMissing).toHaveBeenCalledWith(customerCollection, ...additionalCustomers);
      expect(comp.customersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TrainingStatus query and add missing value', () => {
      const trainee: ITrainee = { id: 456 };
      const trainingStatus: ITrainingStatus = { id: 54315 };
      trainee.trainingStatus = trainingStatus;

      const trainingStatusCollection: ITrainingStatus[] = [{ id: 11514 }];
      jest.spyOn(trainingStatusService, 'query').mockReturnValue(of(new HttpResponse({ body: trainingStatusCollection })));
      const additionalTrainingStatuses = [trainingStatus];
      const expectedCollection: ITrainingStatus[] = [...additionalTrainingStatuses, ...trainingStatusCollection];
      jest.spyOn(trainingStatusService, 'addTrainingStatusToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ trainee });
      comp.ngOnInit();

      expect(trainingStatusService.query).toHaveBeenCalled();
      expect(trainingStatusService.addTrainingStatusToCollectionIfMissing).toHaveBeenCalledWith(
        trainingStatusCollection,
        ...additionalTrainingStatuses
      );
      expect(comp.trainingStatusesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Course query and add missing value', () => {
      const trainee: ITrainee = { id: 456 };
      const course: ICourse = { id: 35561 };
      trainee.course = course;

      const courseCollection: ICourse[] = [{ id: 14119 }];
      jest.spyOn(courseService, 'query').mockReturnValue(of(new HttpResponse({ body: courseCollection })));
      const additionalCourses = [course];
      const expectedCollection: ICourse[] = [...additionalCourses, ...courseCollection];
      jest.spyOn(courseService, 'addCourseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ trainee });
      comp.ngOnInit();

      expect(courseService.query).toHaveBeenCalled();
      expect(courseService.addCourseToCollectionIfMissing).toHaveBeenCalledWith(courseCollection, ...additionalCourses);
      expect(comp.coursesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const trainee: ITrainee = { id: 456 };
      const customer: ICustomer = { id: 34461 };
      trainee.customer = customer;
      const trainingStatus: ITrainingStatus = { id: 24387 };
      trainee.trainingStatus = trainingStatus;
      const course: ICourse = { id: 26529 };
      trainee.course = course;

      activatedRoute.data = of({ trainee });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(trainee));
      expect(comp.customersSharedCollection).toContain(customer);
      expect(comp.trainingStatusesSharedCollection).toContain(trainingStatus);
      expect(comp.coursesSharedCollection).toContain(course);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Trainee>>();
      const trainee = { id: 123 };
      jest.spyOn(traineeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trainee }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(traineeService.update).toHaveBeenCalledWith(trainee);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Trainee>>();
      const trainee = new Trainee();
      jest.spyOn(traineeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trainee }));
      saveSubject.complete();

      // THEN
      expect(traineeService.create).toHaveBeenCalledWith(trainee);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Trainee>>();
      const trainee = { id: 123 };
      jest.spyOn(traineeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(traineeService.update).toHaveBeenCalledWith(trainee);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCustomerById', () => {
      it('Should return tracked Customer primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCustomerById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTrainingStatusById', () => {
      it('Should return tracked TrainingStatus primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTrainingStatusById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCourseById', () => {
      it('Should return tracked Course primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCourseById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
