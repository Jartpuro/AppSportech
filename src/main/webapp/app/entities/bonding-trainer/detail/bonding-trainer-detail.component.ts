import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBondingTrainer } from '../bonding-trainer.model';

@Component({
  selector: 'sportechapp-bonding-trainer-detail',
  templateUrl: './bonding-trainer-detail.component.html',
})
export class BondingTrainerDetailComponent implements OnInit {
  bondingTrainer: IBondingTrainer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bondingTrainer }) => {
      this.bondingTrainer = bondingTrainer;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
