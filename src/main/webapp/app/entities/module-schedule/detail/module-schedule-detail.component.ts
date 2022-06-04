import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModuleSchedule } from '../module-schedule.model';

@Component({
  selector: 'sportechapp-module-schedule-detail',
  templateUrl: './module-schedule-detail.component.html',
})
export class ModuleScheduleDetailComponent implements OnInit {
  moduleSchedule: IModuleSchedule | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moduleSchedule }) => {
      this.moduleSchedule = moduleSchedule;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
