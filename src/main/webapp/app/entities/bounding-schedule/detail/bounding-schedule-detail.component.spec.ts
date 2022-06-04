import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BoundingScheduleDetailComponent } from './bounding-schedule-detail.component';

describe('BoundingSchedule Management Detail Component', () => {
  let comp: BoundingScheduleDetailComponent;
  let fixture: ComponentFixture<BoundingScheduleDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BoundingScheduleDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ boundingSchedule: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BoundingScheduleDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BoundingScheduleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load boundingSchedule on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.boundingSchedule).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
