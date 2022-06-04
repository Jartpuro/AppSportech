import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainingProgram } from '../training-program.model';

@Component({
  selector: 'sportechapp-training-program-detail',
  templateUrl: './training-program-detail.component.html',
})
export class TrainingProgramDetailComponent implements OnInit {
  trainingProgram: ITrainingProgram | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainingProgram }) => {
      this.trainingProgram = trainingProgram;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
