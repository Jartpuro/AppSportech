import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ModuleScheduleDetailComponent } from './module-schedule-detail.component';

describe('ModuleSchedule Management Detail Component', () => {
  let comp: ModuleScheduleDetailComponent;
  let fixture: ComponentFixture<ModuleScheduleDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModuleScheduleDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ moduleSchedule: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ModuleScheduleDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ModuleScheduleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load moduleSchedule on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.moduleSchedule).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
