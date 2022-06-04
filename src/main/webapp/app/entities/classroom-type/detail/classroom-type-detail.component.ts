import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClassroomType } from '../classroom-type.model';

@Component({
  selector: 'sportechapp-classroom-type-detail',
  templateUrl: './classroom-type-detail.component.html',
})
export class ClassroomTypeDetailComponent implements OnInit {
  classroomType: IClassroomType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classroomType }) => {
      this.classroomType = classroomType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
