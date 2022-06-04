import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CampusDetailComponent } from './campus-detail.component';

describe('Campus Management Detail Component', () => {
  let comp: CampusDetailComponent;
  let fixture: ComponentFixture<CampusDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CampusDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ campus: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CampusDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CampusDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load campus on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.campus).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
