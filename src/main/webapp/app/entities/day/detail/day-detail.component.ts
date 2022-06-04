import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDay } from '../day.model';

@Component({
  selector: 'sportechapp-day-detail',
  templateUrl: './day-detail.component.html',
})
export class DayDetailComponent implements OnInit {
  day: IDay | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ day }) => {
      this.day = day;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
