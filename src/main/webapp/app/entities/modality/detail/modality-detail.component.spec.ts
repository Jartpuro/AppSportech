import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ModalityDetailComponent } from './modality-detail.component';

describe('Modality Management Detail Component', () => {
  let comp: ModalityDetailComponent;
  let fixture: ComponentFixture<ModalityDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalityDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ modality: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ModalityDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ModalityDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load modality on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.modality).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
