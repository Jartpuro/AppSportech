import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ScheduleService } from '../service/schedule.service';
import { ISchedule, Schedule } from '../schedule.model';
import { IScheduleVersion } from 'app/entities/schedule-version/schedule-version.model';
import { ScheduleVersionService } from 'app/entities/schedule-version/service/schedule-version.service';
import { IModality } from 'app/entities/modality/modality.model';
import { ModalityService } from 'app/entities/modality/service/modality.service';
import { IDay } from 'app/entities/day/day.model';
import { DayService } from 'app/entities/day/service/day.service';
import { ICourseModule } from 'app/entities/course-module/course-module.model';
import { CourseModuleService } from 'app/entities/course-module/service/course-module.service';
import { IClassroom } from 'app/entities/classroom/classroom.model';
import { ClassroomService } from 'app/entities/classroom/service/classroom.service';
import { ITrainer } from 'app/entities/trainer/trainer.model';
import { TrainerService } from 'app/entities/trainer/service/trainer.service';

import { ScheduleUpdateComponent } from './schedule-update.component';

describe('Schedule Management Update Component', () => {
  let comp: ScheduleUpdateComponent;
  let fixture: ComponentFixture<ScheduleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let scheduleService: ScheduleService;
  let scheduleVersionService: ScheduleVersionService;
  let modalityService: ModalityService;
  let dayService: DayService;
  let courseModuleService: CourseModuleService;
  let classroomService: ClassroomService;
  let trainerService: TrainerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ScheduleUpdateComponent],
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
      .overrideTemplate(ScheduleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ScheduleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    scheduleService = TestBed.inject(ScheduleService);
    scheduleVersionService = TestBed.inject(ScheduleVersionService);
    modalityService = TestBed.inject(ModalityService);
    dayService = TestBed.inject(DayService);
    courseModuleService = TestBed.inject(CourseModuleService);
    classroomService = TestBed.inject(ClassroomService);
    trainerService = TestBed.inject(TrainerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ScheduleVersion query and add missing value', () => {
      const schedule: ISchedule = { id: 456 };
      const scheduleVersion: IScheduleVersion = { id: 98276 };
      schedule.scheduleVersion = scheduleVersion;

      const scheduleVersionCollection: IScheduleVersion[] = [{ id: 48239 }];
      jest.spyOn(scheduleVersionService, 'query').mockReturnValue(of(new HttpResponse({ body: scheduleVersionCollection })));
      const additionalScheduleVersions = [scheduleVersion];
      const expectedCollection: IScheduleVersion[] = [...additionalScheduleVersions, ...scheduleVersionCollection];
      jest.spyOn(scheduleVersionService, 'addScheduleVersionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ schedule });
      comp.ngOnInit();

      expect(scheduleVersionService.query).toHaveBeenCalled();
      expect(scheduleVersionService.addScheduleVersionToCollectionIfMissing).toHaveBeenCalledWith(
        scheduleVersionCollection,
        ...additionalScheduleVersions
      );
      expect(comp.scheduleVersionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Modality query and add missing value', () => {
      const schedule: ISchedule = { id: 456 };
      const modality: IModality = { id: 26482 };
      schedule.modality = modality;

      const modalityCollection: IModality[] = [{ id: 40609 }];
      jest.spyOn(modalityService, 'query').mockReturnValue(of(new HttpResponse({ body: modalityCollection })));
      const additionalModalities = [modality];
      const expectedCollection: IModality[] = [...additionalModalities, ...modalityCollection];
      jest.spyOn(modalityService, 'addModalityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ schedule });
      comp.ngOnInit();

      expect(modalityService.query).toHaveBeenCalled();
      expect(modalityService.addModalityToCollectionIfMissing).toHaveBeenCalledWith(modalityCollection, ...additionalModalities);
      expect(comp.modalitiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Day query and add missing value', () => {
      const schedule: ISchedule = { id: 456 };
      const day: IDay = { id: 23464 };
      schedule.day = day;

      const dayCollection: IDay[] = [{ id: 18957 }];
      jest.spyOn(dayService, 'query').mockReturnValue(of(new HttpResponse({ body: dayCollection })));
      const additionalDays = [day];
      const expectedCollection: IDay[] = [...additionalDays, ...dayCollection];
      jest.spyOn(dayService, 'addDayToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ schedule });
      comp.ngOnInit();

      expect(dayService.query).toHaveBeenCalled();
      expect(dayService.addDayToCollectionIfMissing).toHaveBeenCalledWith(dayCollection, ...additionalDays);
      expect(comp.daysSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CourseModule query and add missing value', () => {
      const schedule: ISchedule = { id: 456 };
      const courseModule: ICourseModule = { id: 60893 };
      schedule.courseModule = courseModule;

      const courseModuleCollection: ICourseModule[] = [{ id: 66730 }];
      jest.spyOn(courseModuleService, 'query').mockReturnValue(of(new HttpResponse({ body: courseModuleCollection })));
      const additionalCourseModules = [courseModule];
      const expectedCollection: ICourseModule[] = [...additionalCourseModules, ...courseModuleCollection];
      jest.spyOn(courseModuleService, 'addCourseModuleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ schedule });
      comp.ngOnInit();

      expect(courseModuleService.query).toHaveBeenCalled();
      expect(courseModuleService.addCourseModuleToCollectionIfMissing).toHaveBeenCalledWith(
        courseModuleCollection,
        ...additionalCourseModules
      );
      expect(comp.courseModulesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Classroom query and add missing value', () => {
      const schedule: ISchedule = { id: 456 };
      const classroom: IClassroom = { id: 82098 };
      schedule.classroom = classroom;

      const classroomCollection: IClassroom[] = [{ id: 49686 }];
      jest.spyOn(classroomService, 'query').mockReturnValue(of(new HttpResponse({ body: classroomCollection })));
      const additionalClassrooms = [classroom];
      const expectedCollection: IClassroom[] = [...additionalClassrooms, ...classroomCollection];
      jest.spyOn(classroomService, 'addClassroomToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ schedule });
      comp.ngOnInit();

      expect(classroomService.query).toHaveBeenCalled();
      expect(classroomService.addClassroomToCollectionIfMissing).toHaveBeenCalledWith(classroomCollection, ...additionalClassrooms);
      expect(comp.classroomsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Trainer query and add missing value', () => {
      const schedule: ISchedule = { id: 456 };
      const trainer: ITrainer = { id: 33740 };
      schedule.trainer = trainer;

      const trainerCollection: ITrainer[] = [{ id: 59112 }];
      jest.spyOn(trainerService, 'query').mockReturnValue(of(new HttpResponse({ body: trainerCollection })));
      const additionalTrainers = [trainer];
      const expectedCollection: ITrainer[] = [...additionalTrainers, ...trainerCollection];
      jest.spyOn(trainerService, 'addTrainerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ schedule });
      comp.ngOnInit();

      expect(trainerService.query).toHaveBeenCalled();
      expect(trainerService.addTrainerToCollectionIfMissing).toHaveBeenCalledWith(trainerCollection, ...additionalTrainers);
      expect(comp.trainersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const schedule: ISchedule = { id: 456 };
      const scheduleVersion: IScheduleVersion = { id: 29584 };
      schedule.scheduleVersion = scheduleVersion;
      const modality: IModality = { id: 3007 };
      schedule.modality = modality;
      const day: IDay = { id: 77852 };
      schedule.day = day;
      const courseModule: ICourseModule = { id: 91621 };
      schedule.courseModule = courseModule;
      const classroom: IClassroom = { id: 75127 };
      schedule.classroom = classroom;
      const trainer: ITrainer = { id: 9602 };
      schedule.trainer = trainer;

      activatedRoute.data = of({ schedule });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(schedule));
      expect(comp.scheduleVersionsSharedCollection).toContain(scheduleVersion);
      expect(comp.modalitiesSharedCollection).toContain(modality);
      expect(comp.daysSharedCollection).toContain(day);
      expect(comp.courseModulesSharedCollection).toContain(courseModule);
      expect(comp.classroomsSharedCollection).toContain(classroom);
      expect(comp.trainersSharedCollection).toContain(trainer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Schedule>>();
      const schedule = { id: 123 };
      jest.spyOn(scheduleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ schedule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: schedule }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(scheduleService.update).toHaveBeenCalledWith(schedule);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Schedule>>();
      const schedule = new Schedule();
      jest.spyOn(scheduleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ schedule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: schedule }));
      saveSubject.complete();

      // THEN
      expect(scheduleService.create).toHaveBeenCalledWith(schedule);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Schedule>>();
      const schedule = { id: 123 };
      jest.spyOn(scheduleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ schedule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(scheduleService.update).toHaveBeenCalledWith(schedule);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackScheduleVersionById', () => {
      it('Should return tracked ScheduleVersion primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackScheduleVersionById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackModalityById', () => {
      it('Should return tracked Modality primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackModalityById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDayById', () => {
      it('Should return tracked Day primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDayById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCourseModuleById', () => {
      it('Should return tracked CourseModule primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCourseModuleById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackClassroomById', () => {
      it('Should return tracked Classroom primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackClassroomById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTrainerById', () => {
      it('Should return tracked Trainer primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTrainerById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
