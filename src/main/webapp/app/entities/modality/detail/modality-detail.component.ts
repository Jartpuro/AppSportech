import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModality } from '../modality.model';

@Component({
  selector: 'sportechapp-modality-detail',
  templateUrl: './modality-detail.component.html',
})
export class ModalityDetailComponent implements OnInit {
  modality: IModality | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modality }) => {
      this.modality = modality;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
