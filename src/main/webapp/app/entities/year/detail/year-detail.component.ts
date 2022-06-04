import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IYear } from '../year.model';

@Component({
  selector: 'sportechapp-year-detail',
  templateUrl: './year-detail.component.html',
})
export class YearDetailComponent implements OnInit {
  year: IYear | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ year }) => {
      this.year = year;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
