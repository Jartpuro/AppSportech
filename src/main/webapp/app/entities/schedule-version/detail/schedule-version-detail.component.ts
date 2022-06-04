import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IScheduleVersion } from '../schedule-version.model';

@Component({
  selector: 'sportechapp-schedule-version-detail',
  templateUrl: './schedule-version-detail.component.html',
})
export class ScheduleVersionDetailComponent implements OnInit {
  scheduleVersion: IScheduleVersion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scheduleVersion }) => {
      this.scheduleVersion = scheduleVersion;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
