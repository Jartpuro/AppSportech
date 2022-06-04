import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBoundingSchedule } from '../bounding-schedule.model';

@Component({
  selector: 'sportechapp-bounding-schedule-detail',
  templateUrl: './bounding-schedule-detail.component.html',
})
export class BoundingScheduleDetailComponent implements OnInit {
  boundingSchedule: IBoundingSchedule | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ boundingSchedule }) => {
      this.boundingSchedule = boundingSchedule;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
