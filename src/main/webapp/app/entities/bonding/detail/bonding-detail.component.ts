import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBonding } from '../bonding.model';

@Component({
  selector: 'sportechapp-bonding-detail',
  templateUrl: './bonding-detail.component.html',
})
export class BondingDetailComponent implements OnInit {
  bonding: IBonding | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bonding }) => {
      this.bonding = bonding;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
