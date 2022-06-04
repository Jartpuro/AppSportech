import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITrainee, Trainee } from '../trainee.model';
import { TraineeService } from '../service/trainee.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { ITrainingStatus } from 'app/entities/training-status/training-status.model';
import { TrainingStatusService } from 'app/entities/training-status/service/training-status.service';
import { ICourse } from 'app/entities/course/course.model';
import { CourseService } from 'app/entities/course/service/course.service';

@Component({
  selector: 'sportechapp-trainee-update',
  templateUrl: './trainee-update.component.html',
})
export class TraineeUpdateComponent implements OnInit {
  isSaving = false;

  customersSharedCollection: ICustomer[] = [];
  trainingStatusesSharedCollection: ITrainingStatus[] = [];
  coursesSharedCollection: ICourse[] = [];

  editForm = this.fb.group({
    id: [],
    customer: [null, Validators.required],
    trainingStatus: [null, Validators.required],
    course: [null, Validators.required],
  });

  constructor(
    protected traineeService: TraineeService,
    protected customerService: CustomerService,
    protected trainingStatusService: TrainingStatusService,
    protected courseService: CourseService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainee }) => {
      this.updateForm(trainee);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trainee = this.createFromForm();
    if (trainee.id !== undefined) {
      this.subscribeToSaveResponse(this.traineeService.update(trainee));
    } else {
      this.subscribeToSaveResponse(this.traineeService.create(trainee));
    }
  }

  trackCustomerById(_index: number, item: ICustomer): number {
    return item.id!;
  }

  trackTrainingStatusById(_index: number, item: ITrainingStatus): number {
    return item.id!;
  }

  trackCourseById(_index: number, item: ICourse): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainee>>): void {
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

  protected updateForm(trainee: ITrainee): void {
    this.editForm.patchValue({
      id: trainee.id,
      customer: trainee.customer,
      trainingStatus: trainee.trainingStatus,
      course: trainee.course,
    });

    this.customersSharedCollection = this.customerService.addCustomerToCollectionIfMissing(
      this.customersSharedCollection,
      trainee.customer
    );
    this.trainingStatusesSharedCollection = this.trainingStatusService.addTrainingStatusToCollectionIfMissing(
      this.trainingStatusesSharedCollection,
      trainee.trainingStatus
    );
    this.coursesSharedCollection = this.courseService.addCourseToCollectionIfMissing(this.coursesSharedCollection, trainee.course);
  }

  protected loadRelationshipsOptions(): void {
    this.customerService
      .query()
      .pipe(map((res: HttpResponse<ICustomer[]>) => res.body ?? []))
      .pipe(
        map((customers: ICustomer[]) =>
          this.customerService.addCustomerToCollectionIfMissing(customers, this.editForm.get('customer')!.value)
        )
      )
      .subscribe((customers: ICustomer[]) => (this.customersSharedCollection = customers));

    this.trainingStatusService
      .query()
      .pipe(map((res: HttpResponse<ITrainingStatus[]>) => res.body ?? []))
      .pipe(
        map((trainingStatuses: ITrainingStatus[]) =>
          this.trainingStatusService.addTrainingStatusToCollectionIfMissing(trainingStatuses, this.editForm.get('trainingStatus')!.value)
        )
      )
      .subscribe((trainingStatuses: ITrainingStatus[]) => (this.trainingStatusesSharedCollection = trainingStatuses));

    this.courseService
      .query()
      .pipe(map((res: HttpResponse<ICourse[]>) => res.body ?? []))
      .pipe(map((courses: ICourse[]) => this.courseService.addCourseToCollectionIfMissing(courses, this.editForm.get('course')!.value)))
      .subscribe((courses: ICourse[]) => (this.coursesSharedCollection = courses));
  }

  protected createFromForm(): ITrainee {
    return {
      ...new Trainee(),
      id: this.editForm.get(['id'])!.value,
      customer: this.editForm.get(['customer'])!.value,
      trainingStatus: this.editForm.get(['trainingStatus'])!.value,
      course: this.editForm.get(['course'])!.value,
    };
  }
}
