import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrainingProgramDetailComponent } from './training-program-detail.component';

describe('TrainingProgram Management Detail Component', () => {
  let comp: TrainingProgramDetailComponent;
  let fixture: ComponentFixture<TrainingProgramDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrainingProgramDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ trainingProgram: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TrainingProgramDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TrainingProgramDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load trainingProgram on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.trainingProgram).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
