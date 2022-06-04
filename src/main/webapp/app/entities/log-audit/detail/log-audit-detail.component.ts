import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILogAudit } from '../log-audit.model';

@Component({
  selector: 'sportechapp-log-audit-detail',
  templateUrl: './log-audit-detail.component.html',
})
export class LogAuditDetailComponent implements OnInit {
  logAudit: ILogAudit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logAudit }) => {
      this.logAudit = logAudit;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
