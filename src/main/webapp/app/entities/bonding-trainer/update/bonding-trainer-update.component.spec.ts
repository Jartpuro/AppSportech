import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BondingTrainerService } from '../service/bonding-trainer.service';
import { IBondingTrainer, BondingTrainer } from '../bonding-trainer.model';
import { IYear } from 'app/entities/year/year.model';
import { YearService } from 'app/entities/year/service/year.service';
import { ITrainer } from 'app/entities/trainer/trainer.model';
import { TrainerService } from 'app/entities/trainer/service/trainer.service';
import { IBonding } from 'app/entities/bonding/bonding.model';
import { BondingService } from 'app/entities/bonding/service/bonding.service';

import { BondingTrainerUpdateComponent } from './bonding-trainer-update.component';

describe('BondingTrainer Management Update Component', () => {
  let comp: BondingTrainerUpdateComponent;
  let fixture: ComponentFixture<BondingTrainerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bondingTrainerService: BondingTrainerService;
  let yearService: YearService;
  let trainerService: TrainerService;
  let bondingService: BondingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BondingTrainerUpdateComponent],
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
      .overrideTemplate(BondingTrainerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BondingTrainerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bondingTrainerService = TestBed.inject(BondingTrainerService);
    yearService = TestBed.inject(YearService);
    trainerService = TestBed.inject(TrainerService);
    bondingService = TestBed.inject(BondingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Year query and add missing value', () => {
      const bondingTrainer: IBondingTrainer = { id: 456 };
      const year: IYear = { id: 61714 };
      bondingTrainer.year = year;

      const yearCollection: IYear[] = [{ id: 20951 }];
      jest.spyOn(yearService, 'query').mockReturnValue(of(new HttpResponse({ body: yearCollection })));
      const additionalYears = [year];
      const expectedCollection: IYear[] = [...additionalYears, ...yearCollection];
      jest.spyOn(yearService, 'addYearToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bondingTrainer });
      comp.ngOnInit();

      expect(yearService.query).toHaveBeenCalled();
      expect(yearService.addYearToCollectionIfMissing).toHaveBeenCalledWith(yearCollection, ...additionalYears);
      expect(comp.yearsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Trainer query and add missing value', () => {
      const bondingTrainer: IBondingTrainer = { id: 456 };
      const trainer: ITrainer = { id: 1043 };
      bondingTrainer.trainer = trainer;

      const trainerCollection: ITrainer[] = [{ id: 16252 }];
      jest.spyOn(trainerService, 'query').mockReturnValue(of(new HttpResponse({ body: trainerCollection })));
      const additionalTrainers = [trainer];
      const expectedCollection: ITrainer[] = [...additionalTrainers, ...trainerCollection];
      jest.spyOn(trainerService, 'addTrainerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bondingTrainer });
      comp.ngOnInit();

      expect(trainerService.query).toHaveBeenCalled();
      expect(trainerService.addTrainerToCollectionIfMissing).toHaveBeenCalledWith(trainerCollection, ...additionalTrainers);
      expect(comp.trainersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Bonding query and add missing value', () => {
      const bondingTrainer: IBondingTrainer = { id: 456 };
      const bonding: IBonding = { id: 50343 };
      bondingTrainer.bonding = bonding;

      const bondingCollection: IBonding[] = [{ id: 59642 }];
      jest.spyOn(bondingService, 'query').mockReturnValue(of(new HttpResponse({ body: bondingCollection })));
      const additionalBondings = [bonding];
      const expectedCollection: IBonding[] = [...additionalBondings, ...bondingCollection];
      jest.spyOn(bondingService, 'addBondingToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bondingTrainer });
      comp.ngOnInit();

      expect(bondingService.query).toHaveBeenCalled();
      expect(bondingService.addBondingToCollectionIfMissing).toHaveBeenCalledWith(bondingCollection, ...additionalBondings);
      expect(comp.bondingsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bondingTrainer: IBondingTrainer = { id: 456 };
      const year: IYear = { id: 36699 };
      bondingTrainer.year = year;
      const trainer: ITrainer = { id: 87426 };
      bondingTrainer.trainer = trainer;
      const bonding: IBonding = { id: 85507 };
      bondingTrainer.bonding = bonding;

      activatedRoute.data = of({ bondingTrainer });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(bondingTrainer));
      expect(comp.yearsSharedCollection).toContain(year);
      expect(comp.trainersSharedCollection).toContain(trainer);
      expect(comp.bondingsSharedCollection).toContain(bonding);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BondingTrainer>>();
      const bondingTrainer = { id: 123 };
      jest.spyOn(bondingTrainerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bondingTrainer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bondingTrainer }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(bondingTrainerService.update).toHaveBeenCalledWith(bondingTrainer);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BondingTrainer>>();
      const bondingTrainer = new BondingTrainer();
      jest.spyOn(bondingTrainerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bondingTrainer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bondingTrainer }));
      saveSubject.complete();

      // THEN
      expect(bondingTrainerService.create).toHaveBeenCalledWith(bondingTrainer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<BondingTrainer>>();
      const bondingTrainer = { id: 123 };
      jest.spyOn(bondingTrainerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bondingTrainer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bondingTrainerService.update).toHaveBeenCalledWith(bondingTrainer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackYearById', () => {
      it('Should return tracked Year primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackYearById(0, entity);
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

    describe('trackBondingById', () => {
      it('Should return tracked Bonding primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackBondingById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
