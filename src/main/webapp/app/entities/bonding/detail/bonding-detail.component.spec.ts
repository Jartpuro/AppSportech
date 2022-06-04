import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BondingDetailComponent } from './bonding-detail.component';

describe('Bonding Management Detail Component', () => {
  let comp: BondingDetailComponent;
  let fixture: ComponentFixture<BondingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BondingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bonding: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BondingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BondingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bonding on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bonding).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
