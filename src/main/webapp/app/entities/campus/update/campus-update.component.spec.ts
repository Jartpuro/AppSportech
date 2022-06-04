import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CampusService } from '../service/campus.service';
import { ICampus, Campus } from '../campus.model';

import { CampusUpdateComponent } from './campus-update.component';

describe('Campus Management Update Component', () => {
  let comp: CampusUpdateComponent;
  let fixture: ComponentFixture<CampusUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let campusService: CampusService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CampusUpdateComponent],
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
      .overrideTemplate(CampusUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CampusUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    campusService = TestBed.inject(CampusService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const campus: ICampus = { id: 456 };

      activatedRoute.data = of({ campus });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(campus));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Campus>>();
      const campus = { id: 123 };
      jest.spyOn(campusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ campus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: campus }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(campusService.update).toHaveBeenCalledWith(campus);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Campus>>();
      const campus = new Campus();
      jest.spyOn(campusService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ campus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: campus }));
      saveSubject.complete();

      // THEN
      expect(campusService.create).toHaveBeenCalledWith(campus);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Campus>>();
      const campus = { id: 123 };
      jest.spyOn(campusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ campus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(campusService.update).toHaveBeenCalledWith(campus);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
