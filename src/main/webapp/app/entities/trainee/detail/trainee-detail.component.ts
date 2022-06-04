import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainee } from '../trainee.model';

@Component({
  selector: 'sportechapp-trainee-detail',
  templateUrl: './trainee-detail.component.html',
})
export class TraineeDetailComponent implements OnInit {
  trainee: ITrainee | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainee }) => {
      this.trainee = trainee;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
