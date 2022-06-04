import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourseStatus } from '../course-status.model';

@Component({
  selector: 'sportechapp-course-status-detail',
  templateUrl: './course-status-detail.component.html',
})
export class CourseStatusDetailComponent implements OnInit {
  courseStatus: ICourseStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courseStatus }) => {
      this.courseStatus = courseStatus;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
