import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IBoundingSchedule, BoundingSchedule } from '../bounding-schedule.model';
import { BoundingScheduleService } from '../service/bounding-schedule.service';
import { IBondingTrainer } from 'app/entities/bonding-trainer/bonding-trainer.model';
import { BondingTrainerService } from 'app/entities/bonding-trainer/service/bonding-trainer.service';

@Component({
  selector: 'sportechapp-bounding-schedule-update',
  templateUrl: './bounding-schedule-update.component.html',
})
export class BoundingScheduleUpdateComponent implements OnInit {
  isSaving = false;

  bondingTrainersSharedCollection: IBondingTrainer[] = [];

  editForm = this.fb.group({
    id: [],
    bondingTrainer: [null, Validators.required],
  });

  constructor(
    protected boundingScheduleService: BoundingScheduleService,
    protected bondingTrainerService: BondingTrainerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ boundingSchedule }) => {
      this.updateForm(boundingSchedule);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const boundingSchedule = this.createFromForm();
    if (boundingSchedule.id !== undefined) {
      this.subscribeToSaveResponse(this.boundingScheduleService.update(boundingSchedule));
    } else {
      this.subscribeToSaveResponse(this.boundingScheduleService.create(boundingSchedule));
    }
  }

  trackBondingTrainerById(_index: number, item: IBondingTrainer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBoundingSchedule>>): void {
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

  protected updateForm(boundingSchedule: IBoundingSchedule): void {
    this.editForm.patchValue({
      id: boundingSchedule.id,
      bondingTrainer: boundingSchedule.bondingTrainer,
    });

    this.bondingTrainersSharedCollection = this.bondingTrainerService.addBondingTrainerToCollectionIfMissing(
      this.bondingTrainersSharedCollection,
      boundingSchedule.bondingTrainer
    );
  }

  protected loadRelationshipsOptions(): void {
    this.bondingTrainerService
      .query()
      .pipe(map((res: HttpResponse<IBondingTrainer[]>) => res.body ?? []))
      .pipe(
        map((bondingTrainers: IBondingTrainer[]) =>
          this.bondingTrainerService.addBondingTrainerToCollectionIfMissing(bondingTrainers, this.editForm.get('bondingTrainer')!.value)
        )
      )
      .subscribe((bondingTrainers: IBondingTrainer[]) => (this.bondingTrainersSharedCollection = bondingTrainers));
  }

  protected createFromForm(): IBoundingSchedule {
    return {
      ...new BoundingSchedule(),
      id: this.editForm.get(['id'])!.value,
      bondingTrainer: this.editForm.get(['bondingTrainer'])!.value,
    };
  }
}
