import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DayDetailComponent } from './day-detail.component';

describe('Day Management Detail Component', () => {
  let comp: DayDetailComponent;
  let fixture: ComponentFixture<DayDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DayDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ day: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DayDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DayDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load day on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.day).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
