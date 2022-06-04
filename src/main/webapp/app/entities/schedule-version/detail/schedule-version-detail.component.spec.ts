import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ScheduleVersionDetailComponent } from './schedule-version-detail.component';

describe('ScheduleVersion Management Detail Component', () => {
  let comp: ScheduleVersionDetailComponent;
  let fixture: ComponentFixture<ScheduleVersionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ScheduleVersionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ scheduleVersion: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ScheduleVersionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ScheduleVersionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load scheduleVersion on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.scheduleVersion).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
