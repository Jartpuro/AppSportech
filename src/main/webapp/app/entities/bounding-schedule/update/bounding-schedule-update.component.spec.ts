import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BoundingScheduleService } from '../service/bounding-schedule.service';
import { IBoundingSchedule, BoundingSchedule } from '../bounding-schedule.model';
import { IBondingTrainer } from 'app/entities/bonding-trainer/bonding-trainer.model';
import { BondingTrainerService } from 'app/entities/bonding-trainer/service/bonding-trainer.service';

import { BoundingScheduleUpdateComponent } from './bounding-schedule-update.component';

describe('BoundingSchedule Management Update Component', () => {
  let comp: BoundingScheduleUpdateComponent;
  let fixture: ComponentFixture<BoundingScheduleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let boundingScheduleService: BoundingScheduleService;
  let bondingTrainerService: BondingTrainerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BoundingScheduleUpdateComponent],
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
      .overrideTemplate(BoundingScheduleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BoundingScheduleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    boundingScheduleService = TestBed.inject(BoundingScheduleService);
    bondingTrainerService = TestBed.inject(BondingTrainerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call BondingTrainer query and add missing value', () => {
      const boundingSchedule: IBoundingSchedule = { id: 456 };
      const bondingTrainer: IBondingTrainer = { id: 13641 };
      boundingSchedule.bondingTrainer = bondingTrainer;

      const bondingTrainerCollection: IBondingTrainer[] = [{ id: 82508 }];
      jest.spyOn(bondingTrainerService, 'query').mockReturnValue(of(new HttpResponse({ body: bondingTrainerCollection })));
      const additionalBondingTrainers = [bondingTrainer];
      const expectedCollection: IBondingTrainer[] = [...additionalBondingTrainers, ...bondingTrainerCollection];
      jest.spyOn(bondingTrainerService, 'addBondingTrainerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ boundingSchedule });
      comp.ngOnInit();

      expect(bondingTrainerService.query).toHaveBeenCalled();
      expect(bondingTrainerService.addBondingTrainerToCollectionIfMissing).toHaveBeenCalledWith(
        bondingTrainerCollection,
        ...additionalBondingTrainers
      );
      expect(comp.bondingTrainersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const boundingSchedule: IBoundingSchedule = { id: 456 };
      const bondingTrainer: IBondingTrainer = { id: 58259 };
      boundingSchedule.bondingTrainer = bondingTrainer;

      activatedRoute.data = of({ boundingSchedule });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(boundingSchedule));
      expect(comp.bondingTrainersSharedCollection).toContain(bondingTrainer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BoundingSchedule>>();
      const boundingSchedule = { id: 123 };
      jest.spyOn(boundingScheduleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ boundingSchedule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: boundingSchedule }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(boundingScheduleService.update).toHaveBeenCalledWith(boundingSchedule);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BoundingSchedule>>();
      const boundingSchedule = new BoundingSchedule();
      jest.spyOn(boundingScheduleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ boundingSchedule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: boundingSchedule }));
      saveSubject.complete();

      // THEN
      expect(boundingScheduleService.create).toHaveBeenCalledWith(boundingSchedule);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BoundingSchedule>>();
      const boundingSchedule = { id: 123 };
      jest.spyOn(boundingScheduleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ boundingSchedule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(boundingScheduleService.update).toHaveBeenCalledWith(boundingSchedule);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackBondingTrainerById', () => {
      it('Should return tracked BondingTrainer primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackBondingTrainerById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
