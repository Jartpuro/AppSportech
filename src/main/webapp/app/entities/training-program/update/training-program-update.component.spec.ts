import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TrainingProgramService } from '../service/training-program.service';
import { ITrainingProgram, TrainingProgram } from '../training-program.model';

import { TrainingProgramUpdateComponent } from './training-program-update.component';

describe('TrainingProgram Management Update Component', () => {
  let comp: TrainingProgramUpdateComponent;
  let fixture: ComponentFixture<TrainingProgramUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let trainingProgramService: TrainingProgramService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TrainingProgramUpdateComponent],
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
      .overrideTemplate(TrainingProgramUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TrainingProgramUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    trainingProgramService = TestBed.inject(TrainingProgramService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const trainingProgram: ITrainingProgram = { id: 456 };

      activatedRoute.data = of({ trainingProgram });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(trainingProgram));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TrainingProgram>>();
      const trainingProgram = { id: 123 };
      jest.spyOn(trainingProgramService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainingProgram });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trainingProgram }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(trainingProgramService.update).toHaveBeenCalledWith(trainingProgram);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TrainingProgram>>();
      const trainingProgram = new TrainingProgram();
      jest.spyOn(trainingProgramService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainingProgram });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trainingProgram }));
      saveSubject.complete();

      // THEN
      expect(trainingProgramService.create).toHaveBeenCalledWith(trainingProgram);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TrainingProgram>>();
      const trainingProgram = { id: 123 };
      jest.spyOn(trainingProgramService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainingProgram });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(trainingProgramService.update).toHaveBeenCalledWith(trainingProgram);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
