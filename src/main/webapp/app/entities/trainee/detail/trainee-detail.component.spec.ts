import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TraineeDetailComponent } from './trainee-detail.component';

describe('Trainee Management Detail Component', () => {
  let comp: TraineeDetailComponent;
  let fixture: ComponentFixture<TraineeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TraineeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ trainee: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TraineeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TraineeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load trainee on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.trainee).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
