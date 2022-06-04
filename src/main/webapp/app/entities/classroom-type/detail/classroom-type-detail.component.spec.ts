import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ClassroomTypeDetailComponent } from './classroom-type-detail.component';

describe('ClassroomType Management Detail Component', () => {
  let comp: ClassroomTypeDetailComponent;
  let fixture: ComponentFixture<ClassroomTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClassroomTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ classroomType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ClassroomTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ClassroomTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load classroomType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.classroomType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
