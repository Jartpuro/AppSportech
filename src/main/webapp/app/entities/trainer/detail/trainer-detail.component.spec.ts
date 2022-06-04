import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrainerDetailComponent } from './trainer-detail.component';

describe('Trainer Management Detail Component', () => {
  let comp: TrainerDetailComponent;
  let fixture: ComponentFixture<TrainerDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrainerDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ trainer: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TrainerDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TrainerDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load trainer on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.trainer).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
