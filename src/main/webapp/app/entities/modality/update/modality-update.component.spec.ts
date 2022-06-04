import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ModalityService } from '../service/modality.service';
import { IModality, Modality } from '../modality.model';

import { ModalityUpdateComponent } from './modality-update.component';

describe('Modality Management Update Component', () => {
  let comp: ModalityUpdateComponent;
  let fixture: ComponentFixture<ModalityUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let modalityService: ModalityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ModalityUpdateComponent],
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
      .overrideTemplate(ModalityUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ModalityUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    modalityService = TestBed.inject(ModalityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const modality: IModality = { id: 456 };

      activatedRoute.data = of({ modality });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(modality));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Modality>>();
      const modality = { id: 123 };
      jest.spyOn(modalityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modality });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: modality }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(modalityService.update).toHaveBeenCalledWith(modality);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Modality>>();
      const modality = new Modality();
      jest.spyOn(modalityService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modality });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: modality }));
      saveSubject.complete();

      // THEN
      expect(modalityService.create).toHaveBeenCalledWith(modality);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Modality>>();
      const modality = { id: 123 };
      jest.spyOn(modalityService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ modality });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(modalityService.update).toHaveBeenCalledWith(modality);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
