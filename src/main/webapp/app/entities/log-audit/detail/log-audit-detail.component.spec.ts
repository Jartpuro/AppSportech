import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LogAuditDetailComponent } from './log-audit-detail.component';

describe('LogAudit Management Detail Component', () => {
  let comp: LogAuditDetailComponent;
  let fixture: ComponentFixture<LogAuditDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LogAuditDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ logAudit: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LogAuditDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LogAuditDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load logAudit on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.logAudit).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
