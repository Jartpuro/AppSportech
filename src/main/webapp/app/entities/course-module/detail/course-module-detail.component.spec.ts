import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CourseModuleDetailComponent } from './course-module-detail.component';

describe('CourseModule Management Detail Component', () => {
  let comp: CourseModuleDetailComponent;
  let fixture: ComponentFixture<CourseModuleDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CourseModuleDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ courseModule: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CourseModuleDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CourseModuleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load courseModule on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.courseModule).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
