import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CourseStatusDetailComponent } from './course-status-detail.component';

describe('CourseStatus Management Detail Component', () => {
  let comp: CourseStatusDetailComponent;
  let fixture: ComponentFixture<CourseStatusDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CourseStatusDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ courseStatus: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CourseStatusDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CourseStatusDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load courseStatus on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.courseStatus).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
