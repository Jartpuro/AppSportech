import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { YearService } from '../service/year.service';
import { IYear, Year } from '../year.model';

import { YearUpdateComponent } from './year-update.component';

describe('Year Management Update Component', () => {
  let comp: YearUpdateComponent;
  let fixture: ComponentFixture<YearUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let yearService: YearService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [YearUpdateComponent],
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
      .overrideTemplate(YearUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(YearUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    yearService = TestBed.inject(YearService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const year: IYear = { id: 456 };

      activatedRoute.data = of({ year });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(year));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Year>>();
      const year = { id: 123 };
      jest.spyOn(yearService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ year });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: year }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(yearService.update).toHaveBeenCalledWith(year);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Year>>();
      const year = new Year();
      jest.spyOn(yearService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ year });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: year }));
      saveSubject.complete();

      // THEN
      expect(yearService.create).toHaveBeenCalledWith(year);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Year>>();
      const year = { id: 123 };
      jest.spyOn(yearService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ year });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(yearService.update).toHaveBeenCalledWith(year);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
