import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainingStatus } from '../training-status.model';

@Component({
  selector: 'sportechapp-training-status-detail',
  templateUrl: './training-status-detail.component.html',
})
export class TrainingStatusDetailComponent implements OnInit {
  trainingStatus: ITrainingStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainingStatus }) => {
      this.trainingStatus = trainingStatus;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
