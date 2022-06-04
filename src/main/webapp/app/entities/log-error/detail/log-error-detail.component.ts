import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILogError } from '../log-error.model';

@Component({
  selector: 'sportechapp-log-error-detail',
  templateUrl: './log-error-detail.component.html',
})
export class LogErrorDetailComponent implements OnInit {
  logError: ILogError | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ logError }) => {
      this.logError = logError;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
