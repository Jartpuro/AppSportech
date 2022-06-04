import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICampus } from '../campus.model';

@Component({
  selector: 'sportechapp-campus-detail',
  templateUrl: './campus-detail.component.html',
})
export class CampusDetailComponent implements OnInit {
  campus: ICampus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ campus }) => {
      this.campus = campus;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
