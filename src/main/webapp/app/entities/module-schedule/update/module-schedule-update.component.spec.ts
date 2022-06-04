import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ModuleScheduleService } from '../service/module-schedule.service';
import { IModuleSchedule, ModuleSchedule } from '../module-schedule.model';
import { IModule } from 'app/entities/module/module.model';
import { ModuleService } from 'app/entities/module/service/module.service';

import { ModuleScheduleUpdateComponent } from './module-schedule-update.component';

describe('ModuleSchedule Management Update Component', () => {
  let comp: ModuleScheduleUpdateComponent;
  let fixture: ComponentFixture<ModuleScheduleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let moduleScheduleService: ModuleScheduleService;
  let moduleService: ModuleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ModuleScheduleUpdateComponent],
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
      .overrideTemplate(ModuleScheduleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ModuleScheduleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    moduleScheduleService = TestBed.inject(ModuleScheduleService);
    moduleService = TestBed.inject(ModuleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Module query and add missing value', () => {
      const moduleSchedule: IModuleSchedule = { id: 456 };
      const module: IModule = { id: 43143 };
      moduleSchedule.module = module;

      const moduleCollection: IModule[] = [{ id: 6983 }];
      jest.spyOn(moduleService, 'query').mockReturnValue(of(new HttpResponse({ body: moduleCollection })));
      const additionalModules = [module];
      const expectedCollection: IModule[] = [...additionalModules, ...moduleCollection];
      jest.spyOn(moduleService, 'addModuleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ moduleSchedule });
      comp.ngOnInit();

      expect(moduleService.query).toHaveBeenCalled();
      expect(moduleService.addModuleToCollectionIfMissing).toHaveBeenCalledWith(moduleCollection, ...additionalModules);
      expect(comp.modulesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const moduleSchedule: IModuleSchedule = { id: 456 };
      const module: IModule = { id: 14123 };
      moduleSchedule.module = module;

      activatedRoute.data = of({ moduleSchedule });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(moduleSchedule));
      expect(comp.modulesSharedCollection).toContain(module);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ModuleSchedule>>();
      const moduleSchedule = { id: 123 };
      jest.spyOn(moduleScheduleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moduleSchedule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: moduleSchedule }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(moduleScheduleService.update).toHaveBeenCalledWith(moduleSchedule);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ModuleSchedule>>();
      const moduleSchedule = new ModuleSchedule();
      jest.spyOn(moduleScheduleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moduleSchedule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: moduleSchedule }));
      saveSubject.complete();

      // THEN
      expect(moduleScheduleService.create).toHaveBeenCalledWith(moduleSchedule);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ModuleSchedule>>();
      const moduleSchedule = { id: 123 };
      jest.spyOn(moduleScheduleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ moduleSchedule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(moduleScheduleService.update).toHaveBeenCalledWith(moduleSchedule);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackModuleById', () => {
      it('Should return tracked Module primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackModuleById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
