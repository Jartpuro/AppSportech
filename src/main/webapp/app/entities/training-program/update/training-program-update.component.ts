import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITrainingProgram, TrainingProgram } from '../training-program.model';
import { TrainingProgramService } from '../service/training-program.service';
import { Offer } from 'app/entities/enumerations/offer.model';
import { StateProgram } from 'app/entities/enumerations/state-program.model';

@Component({
  selector: 'sportechapp-training-program-update',
  templateUrl: './training-program-update.component.html',
})
export class TrainingProgramUpdateComponent implements OnInit {
  isSaving = false;
  offerValues = Object.keys(Offer);
  stateProgramValues = Object.keys(StateProgram);

  editForm = this.fb.group({
    id: [],
    programCode: [null, [Validators.required, Validators.maxLength(50)]],
    programVersion: [null, [Validators.required, Validators.maxLength(40)]],
    programName: [null, [Validators.required]],
    programInitials: [null, [Validators.required, Validators.maxLength(40)]],
    programState: [null, [Validators.required]],
  });

  constructor(
    protected trainingProgramService: TrainingProgramService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainingProgram }) => {
      this.updateForm(trainingProgram);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trainingProgram = this.createFromForm();
    if (trainingProgram.id !== undefined) {
      this.subscribeToSaveResponse(this.trainingProgramService.update(trainingProgram));
    } else {
      this.subscribeToSaveResponse(this.trainingProgramService.create(trainingProgram));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainingProgram>>): void {
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

  protected updateForm(trainingProgram: ITrainingProgram): void {
    this.editForm.patchValue({
      id: trainingProgram.id,
      programCode: trainingProgram.programCode,
      programVersion: trainingProgram.programVersion,
      programName: trainingProgram.programName,
      programInitials: trainingProgram.programInitials,
      programState: trainingProgram.programState,
    });
  }

  protected createFromForm(): ITrainingProgram {
    return {
      ...new TrainingProgram(),
      id: this.editForm.get(['id'])!.value,
      programCode: this.editForm.get(['programCode'])!.value,
      programVersion: this.editForm.get(['programVersion'])!.value,
      programName: this.editForm.get(['programName'])!.value,
      programInitials: this.editForm.get(['programInitials'])!.value,
      programState: this.editForm.get(['programState'])!.value,
    };
  }
}
