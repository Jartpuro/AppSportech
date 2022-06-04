import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITrainingStatus, TrainingStatus } from '../training-status.model';
import { TrainingStatusService } from '../service/training-status.service';
import { State } from 'app/entities/enumerations/state.model';

@Component({
  selector: 'sportechapp-training-status-update',
  templateUrl: './training-status-update.component.html',
})
export class TrainingStatusUpdateComponent implements OnInit {
  isSaving = false;
  stateValues = Object.keys(State);

  editForm = this.fb.group({
    id: [],
    statusName: [null, [Validators.required, Validators.maxLength(40)]],
    stateTraining: [null, [Validators.required]],
  });

  constructor(
    protected trainingStatusService: TrainingStatusService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainingStatus }) => {
      this.updateForm(trainingStatus);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trainingStatus = this.createFromForm();
    if (trainingStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.trainingStatusService.update(trainingStatus));
    } else {
      this.subscribeToSaveResponse(this.trainingStatusService.create(trainingStatus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainingStatus>>): void {
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

  protected updateForm(trainingStatus: ITrainingStatus): void {
    this.editForm.patchValue({
      id: trainingStatus.id,
      statusName: trainingStatus.statusName,
      stateTraining: trainingStatus.stateTraining,
    });
  }

  protected createFromForm(): ITrainingStatus {
    return {
      ...new TrainingStatus(),
      id: this.editForm.get(['id'])!.value,
      statusName: this.editForm.get(['statusName'])!.value,
      stateTraining: this.editForm.get(['stateTraining'])!.value,
    };
  }
}
