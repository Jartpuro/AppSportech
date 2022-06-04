import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AreaTrainerService } from '../service/area-trainer.service';
import { IAreaTrainer, AreaTrainer } from '../area-trainer.model';
import { IArea } from 'app/entities/area/area.model';
import { AreaService } from 'app/entities/area/service/area.service';
import { ITrainer } from 'app/entities/trainer/trainer.model';
import { TrainerService } from 'app/entities/trainer/service/trainer.service';

import { AreaTrainerUpdateComponent } from './area-trainer-update.component';

describe('AreaTrainer Management Update Component', () => {
  let comp: AreaTrainerUpdateComponent;
  let fixture: ComponentFixture<AreaTrainerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let areaTrainerService: AreaTrainerService;
  let areaService: AreaService;
  let trainerService: TrainerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AreaTrainerUpdateComponent],
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
      .overrideTemplate(AreaTrainerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AreaTrainerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    areaTrainerService = TestBed.inject(AreaTrainerService);
    areaService = TestBed.inject(AreaService);
    trainerService = TestBed.inject(TrainerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Area query and add missing value', () => {
      const areaTrainer: IAreaTrainer = { id: 456 };
      const area: IArea = { id: 85206 };
      areaTrainer.area = area;

      const areaCollection: IArea[] = [{ id: 56389 }];
      jest.spyOn(areaService, 'query').mockReturnValue(of(new HttpResponse({ body: areaCollection })));
      const additionalAreas = [area];
      const expectedCollection: IArea[] = [...additionalAreas, ...areaCollection];
      jest.spyOn(areaService, 'addAreaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ areaTrainer });
      comp.ngOnInit();

      expect(areaService.query).toHaveBeenCalled();
      expect(areaService.addAreaToCollectionIfMissing).toHaveBeenCalledWith(areaCollection, ...additionalAreas);
      expect(comp.areasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Trainer query and add missing value', () => {
      const areaTrainer: IAreaTrainer = { id: 456 };
      const trainer: ITrainer = { id: 49227 };
      areaTrainer.trainer = trainer;

      const trainerCollection: ITrainer[] = [{ id: 52788 }];
      jest.spyOn(trainerService, 'query').mockReturnValue(of(new HttpResponse({ body: trainerCollection })));
      const additionalTrainers = [trainer];
      const expectedCollection: ITrainer[] = [...additionalTrainers, ...trainerCollection];
      jest.spyOn(trainerService, 'addTrainerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ areaTrainer });
      comp.ngOnInit();

      expect(trainerService.query).toHaveBeenCalled();
      expect(trainerService.addTrainerToCollectionIfMissing).toHaveBeenCalledWith(trainerCollection, ...additionalTrainers);
      expect(comp.trainersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const areaTrainer: IAreaTrainer = { id: 456 };
      const area: IArea = { id: 53562 };
      areaTrainer.area = area;
      const trainer: ITrainer = { id: 35336 };
      areaTrainer.trainer = trainer;

      activatedRoute.data = of({ areaTrainer });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(areaTrainer));
      expect(comp.areasSharedCollection).toContain(area);
      expect(comp.trainersSharedCollection).toContain(trainer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AreaTrainer>>();
      const areaTrainer = { id: 123 };
      jest.spyOn(areaTrainerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaTrainer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: areaTrainer }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(areaTrainerService.update).toHaveBeenCalledWith(areaTrainer);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AreaTrainer>>();
      const areaTrainer = new AreaTrainer();
      jest.spyOn(areaTrainerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaTrainer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: areaTrainer }));
      saveSubject.complete();

      // THEN
      expect(areaTrainerService.create).toHaveBeenCalledWith(areaTrainer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AreaTrainer>>();
      const areaTrainer = { id: 123 };
      jest.spyOn(areaTrainerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ areaTrainer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(areaTrainerService.update).toHaveBeenCalledWith(areaTrainer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAreaById', () => {
      it('Should return tracked Area primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAreaById(0, entity);
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
