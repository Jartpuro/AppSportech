import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICourseStatus, CourseStatus } from '../course-status.model';
import { CourseStatusService } from '../service/course-status.service';
import { State } from 'app/entities/enumerations/state.model';

@Component({
  selector: 'sportechapp-course-status-update',
  templateUrl: './course-status-update.component.html',
})
export class CourseStatusUpdateComponent implements OnInit {
  isSaving = false;
  stateValues = Object.keys(State);

  editForm = this.fb.group({
    id: [],
    nameCourseStatus: [null, [Validators.required, Validators.maxLength(20)]],
    stateCourse: [null, [Validators.required]],
  });

  constructor(protected courseStatusService: CourseStatusService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courseStatus }) => {
      this.updateForm(courseStatus);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const courseStatus = this.createFromForm();
    if (courseStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.courseStatusService.update(courseStatus));
    } else {
      this.subscribeToSaveResponse(this.courseStatusService.create(courseStatus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourseStatus>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(courseStatus: ICourseStatus): void {
    this.editForm.patchValue({
      id: courseStatus.id,
      nameCourseStatus: courseStatus.nameCourseStatus,
      stateCourse: courseStatus.stateCourse,
    });
  }

  protected createFromForm(): ICourseStatus {
    return {
      ...new CourseStatus(),
      id: this.editForm.get(['id'])!.value,
      nameCourseStatus: this.editForm.get(['nameCourseStatus'])!.value,
      stateCourse: this.editForm.get(['stateCourse'])!.value,
    };
  }
}
