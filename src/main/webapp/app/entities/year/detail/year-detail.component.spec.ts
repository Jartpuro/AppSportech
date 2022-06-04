import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YearDetailComponent } from './year-detail.component';

describe('Year Management Detail Component', () => {
  let comp: YearDetailComponent;
  let fixture: ComponentFixture<YearDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [YearDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ year: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(YearDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(YearDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load year on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.year).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
