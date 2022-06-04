import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BondingTrainerDetailComponent } from './bonding-trainer-detail.component';

describe('BondingTrainer Management Detail Component', () => {
  let comp: BondingTrainerDetailComponent;
  let fixture: ComponentFixture<BondingTrainerDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BondingTrainerDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bondingTrainer: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BondingTrainerDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BondingTrainerDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bondingTrainer on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bondingTrainer).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
