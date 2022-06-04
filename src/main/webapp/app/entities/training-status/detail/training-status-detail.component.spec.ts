import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrainingStatusDetailComponent } from './training-status-detail.component';

describe('TrainingStatus Management Detail Component', () => {
  let comp: TrainingStatusDetailComponent;
  let fixture: ComponentFixture<TrainingStatusDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrainingStatusDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ trainingStatus: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TrainingStatusDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TrainingStatusDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load trainingStatus on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.trainingStatus).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
