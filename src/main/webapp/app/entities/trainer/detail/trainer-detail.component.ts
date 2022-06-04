import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainer } from '../trainer.model';

@Component({
  selector: 'sportechapp-trainer-detail',
  templateUrl: './trainer-detail.component.html',
})
export class TrainerDetailComponent implements OnInit {
  trainer: ITrainer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainer }) => {
      this.trainer = trainer;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
