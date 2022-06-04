import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ScheduleVersionService } from '../service/schedule-version.service';
import { IScheduleVersion, ScheduleVersion } from '../schedule-version.model';

import { ScheduleVersionUpdateComponent } from './schedule-version-update.component';

describe('ScheduleVersion Management Update Component', () => {
  let comp: ScheduleVersionUpdateComponent;
  let fixture: ComponentFixture<ScheduleVersionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let scheduleVersionService: ScheduleVersionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ScheduleVersionUpdateComponent],
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
      .overrideTemplate(ScheduleVersionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ScheduleVersionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    scheduleVersionService = TestBed.inject(ScheduleVersionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const scheduleVersion: IScheduleVersion = { id: 456 };

      activatedRoute.data = of({ scheduleVersion });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(scheduleVersion));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ScheduleVersion>>();
      const scheduleVersion = { id: 123 };
      jest.spyOn(scheduleVersionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ scheduleVersion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: scheduleVersion }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(scheduleVersionService.update).toHaveBeenCalledWith(scheduleVersion);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ScheduleVersion>>();
      const scheduleVersion = new ScheduleVersion();
      jest.spyOn(scheduleVersionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ scheduleVersion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: scheduleVersion }));
      saveSubject.complete();

      // THEN
      expect(scheduleVersionService.create).toHaveBeenCalledWith(scheduleVersion);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ScheduleVersion>>();
      const scheduleVersion = { id: 123 };
      jest.spyOn(scheduleVersionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ scheduleVersion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(scheduleVersionService.update).toHaveBeenCalledWith(scheduleVersion);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
