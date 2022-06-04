import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AreaTrainerDetailComponent } from './area-trainer-detail.component';

describe('AreaTrainer Management Detail Component', () => {
  let comp: AreaTrainerDetailComponent;
  let fixture: ComponentFixture<AreaTrainerDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AreaTrainerDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ areaTrainer: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AreaTrainerDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AreaTrainerDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load areaTrainer on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.areaTrainer).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
