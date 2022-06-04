import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BondingService } from '../service/bonding.service';
import { IBonding, Bonding } from '../bonding.model';

import { BondingUpdateComponent } from './bonding-update.component';

describe('Bonding Management Update Component', () => {
  let comp: BondingUpdateComponent;
  let fixture: ComponentFixture<BondingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bondingService: BondingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BondingUpdateComponent],
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
      .overrideTemplate(BondingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BondingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bondingService = TestBed.inject(BondingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const bonding: IBonding = { id: 456 };

      activatedRoute.data = of({ bonding });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(bonding));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Bonding>>();
      const bonding = { id: 123 };
      jest.spyOn(bondingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bonding });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bonding }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(bondingService.update).toHaveBeenCalledWith(bonding);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Bonding>>();
      const bonding = new Bonding();
      jest.spyOn(bondingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bonding });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bonding }));
      saveSubject.complete();

      // THEN
      expect(bondingService.create).toHaveBeenCalledWith(bonding);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Bonding>>();
      const bonding = { id: 123 };
      jest.spyOn(bondingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bonding });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bondingService.update).toHaveBeenCalledWith(bonding);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
