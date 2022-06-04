import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAreaTrainer } from '../area-trainer.model';

@Component({
  selector: 'sportechapp-area-trainer-detail',
  templateUrl: './area-trainer-detail.component.html',
})
export class AreaTrainerDetailComponent implements OnInit {
  areaTrainer: IAreaTrainer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ areaTrainer }) => {
      this.areaTrainer = areaTrainer;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
