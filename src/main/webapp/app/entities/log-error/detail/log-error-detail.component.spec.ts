import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogErrorDetailComponent } from './log-error-detail.component';

describe('LogError Management Detail Component', () => {
  let comp: LogErrorDetailComponent;
  let fixture: ComponentFixture<LogErrorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LogErrorDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ logError: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LogErrorDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LogErrorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load logError on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.logError).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
