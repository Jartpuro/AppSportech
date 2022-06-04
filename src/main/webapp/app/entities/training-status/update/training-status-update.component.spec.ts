import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TrainingStatusService } from '../service/training-status.service';
import { ITrainingStatus, TrainingStatus } from '../training-status.model';

import { TrainingStatusUpdateComponent } from './training-status-update.component';

describe('TrainingStatus Management Update Component', () => {
  let comp: TrainingStatusUpdateComponent;
  let fixture: ComponentFixture<TrainingStatusUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let trainingStatusService: TrainingStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TrainingStatusUpdateComponent],
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
      .overrideTemplate(TrainingStatusUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TrainingStatusUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    trainingStatusService = TestBed.inject(TrainingStatusService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const trainingStatus: ITrainingStatus = { id: 456 };

      activatedRoute.data = of({ trainingStatus });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(trainingStatus));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TrainingStatus>>();
      const trainingStatus = { id: 123 };
      jest.spyOn(trainingStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainingStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trainingStatus }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(trainingStatusService.update).toHaveBeenCalledWith(trainingStatus);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TrainingStatus>>();
      const trainingStatus = new TrainingStatus();
      jest.spyOn(trainingStatusService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainingStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trainingStatus }));
      saveSubject.complete();

      // THEN
      expect(trainingStatusService.create).toHaveBeenCalledWith(trainingStatus);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TrainingStatus>>();
      const trainingStatus = { id: 123 };
      jest.spyOn(trainingStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainingStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(trainingStatusService.update).toHaveBeenCalledWith(trainingStatus);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
