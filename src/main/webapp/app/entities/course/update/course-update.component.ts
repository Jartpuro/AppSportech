import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICourse, Course } from '../course.model';
import { CourseService } from '../service/course.service';
import { ICourseStatus } from 'app/entities/course-status/course-status.model';
import { CourseStatusService } from 'app/entities/course-status/service/course-status.service';
import { ITrainingProgram } from 'app/entities/training-program/training-program.model';
import { TrainingProgramService } from 'app/entities/training-program/service/training-program.service';

@Component({
  selector: 'sportechapp-course-update',
  templateUrl: './course-update.component.html',
})
export class CourseUpdateComponent implements OnInit {
  isSaving = false;

  courseStatusesSharedCollection: ICourseStatus[] = [];
  trainingProgramsSharedCollection: ITrainingProgram[] = [];

  editForm = this.fb.group({
    id: [],
    courseNumber: [null, [Validators.required, Validators.maxLength(100)]],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    route: [null, [Validators.required, Validators.maxLength(40)]],
    courseStatus: [null, Validators.required],
    trainingProgram: [null, Validators.required],
  });

  constructor(
    protected courseService: CourseService,
    protected courseStatusService: CourseStatusService,
    protected trainingProgramService: TrainingProgramService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ course }) => {
      this.updateForm(course);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const course = this.createFromForm();
    if (course.id !== undefined) {
      this.subscribeToSaveResponse(this.courseService.update(course));
    } else {
      this.subscribeToSaveResponse(this.courseService.create(course));
    }
  }

  trackCourseStatusById(_index: number, item: ICourseStatus): number {
    return item.id!;
  }

  trackTrainingProgramById(_index: number, item: ITrainingProgram): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourse>>): void {
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

  protected updateForm(course: ICourse): void {
    this.editForm.patchValue({
      id: course.id,
      courseNumber: course.courseNumber,
      startDate: course.startDate,
      endDate: course.endDate,
      route: course.route,
      courseStatus: course.courseStatus,
      trainingProgram: course.trainingProgram,
    });

    this.courseStatusesSharedCollection = this.courseStatusService.addCourseStatusToCollectionIfMissing(
      this.courseStatusesSharedCollection,
      course.courseStatus
    );
    this.trainingProgramsSharedCollection = this.trainingProgramService.addTrainingProgramToCollectionIfMissing(
      this.trainingProgramsSharedCollection,
      course.trainingProgram
    );
  }

  protected loadRelationshipsOptions(): void {
    this.courseStatusService
      .query()
      .pipe(map((res: HttpResponse<ICourseStatus[]>) => res.body ?? []))
      .pipe(
        map((courseStatuses: ICourseStatus[]) =>
          this.courseStatusService.addCourseStatusToCollectionIfMissing(courseStatuses, this.editForm.get('courseStatus')!.value)
        )
      )
      .subscribe((courseStatuses: ICourseStatus[]) => (this.courseStatusesSharedCollection = courseStatuses));

    this.trainingProgramService
      .query()
      .pipe(map((res: HttpResponse<ITrainingProgram[]>) => res.body ?? []))
      .pipe(
        map((trainingPrograms: ITrainingProgram[]) =>
          this.trainingProgramService.addTrainingProgramToCollectionIfMissing(trainingPrograms, this.editForm.get('trainingProgram')!.value)
        )
      )
      .subscribe((trainingPrograms: ITrainingProgram[]) => (this.trainingProgramsSharedCollection = trainingPrograms));
  }

  protected createFromForm(): ICourse {
    return {
      ...new Course(),
      id: this.editForm.get(['id'])!.value,
      courseNumber: this.editForm.get(['courseNumber'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      route: this.editForm.get(['route'])!.value,
      courseStatus: this.editForm.get(['courseStatus'])!.value,
      trainingProgram: this.editForm.get(['trainingProgram'])!.value,
    };
  }
}
