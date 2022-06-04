import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAreaTrainer, AreaTrainer } from '../area-trainer.model';
import { AreaTrainerService } from '../service/area-trainer.service';
import { IArea } from 'app/entities/area/area.model';
import { AreaService } from 'app/entities/area/service/area.service';
import { ITrainer } from 'app/entities/trainer/trainer.model';
import { TrainerService } from 'app/entities/trainer/service/trainer.service';
import { State } from 'app/entities/enumerations/state.model';

@Component({
  selector: 'sportechapp-area-trainer-update',
  templateUrl: './area-trainer-update.component.html',
})
export class AreaTrainerUpdateComponent implements OnInit {
  isSaving = false;
  stateValues = Object.keys(State);

  areasSharedCollection: IArea[] = [];
  trainersSharedCollection: ITrainer[] = [];

  editForm = this.fb.group({
    id: [],
    areaTrainerState: [null, [Validators.required]],
    area: [null, Validators.required],
    trainer: [null, Validators.required],
  });

  constructor(
    protected areaTrainerService: AreaTrainerService,
    protected areaService: AreaService,
    protected trainerService: TrainerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ areaTrainer }) => {
      this.updateForm(areaTrainer);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const areaTrainer = this.createFromForm();
    if (areaTrainer.id !== undefined) {
      this.subscribeToSaveResponse(this.areaTrainerService.update(areaTrainer));
    } else {
      this.subscribeToSaveResponse(this.areaTrainerService.create(areaTrainer));
    }
  }

  trackAreaById(_index: number, item: IArea): number {
    return item.id!;
  }

  trackTrainerById(_index: number, item: ITrainer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAreaTrainer>>): void {
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

  protected updateForm(areaTrainer: IAreaTrainer): void {
    this.editForm.patchValue({
      id: areaTrainer.id,
      areaTrainerState: areaTrainer.areaTrainerState,
      area: areaTrainer.area,
      trainer: areaTrainer.trainer,
    });

    this.areasSharedCollection = this.areaService.addAreaToCollectionIfMissing(this.areasSharedCollection, areaTrainer.area);
    this.trainersSharedCollection = this.trainerService.addTrainerToCollectionIfMissing(this.trainersSharedCollection, areaTrainer.trainer);
  }

  protected loadRelationshipsOptions(): void {
    this.areaService
      .query()
      .pipe(map((res: HttpResponse<IArea[]>) => res.body ?? []))
      .pipe(map((areas: IArea[]) => this.areaService.addAreaToCollectionIfMissing(areas, this.editForm.get('area')!.value)))
      .subscribe((areas: IArea[]) => (this.areasSharedCollection = areas));

    this.trainerService
      .query()
      .pipe(map((res: HttpResponse<ITrainer[]>) => res.body ?? []))
      .pipe(
        map((trainers: ITrainer[]) => this.trainerService.addTrainerToCollectionIfMissing(trainers, this.editForm.get('trainer')!.value))
      )
      .subscribe((trainers: ITrainer[]) => (this.trainersSharedCollection = trainers));
  }

  protected createFromForm(): IAreaTrainer {
    return {
      ...new AreaTrainer(),
      id: this.editForm.get(['id'])!.value,
      areaTrainerState: this.editForm.get(['areaTrainerState'])!.value,
      area: this.editForm.get(['area'])!.value,
      trainer: this.editForm.get(['trainer'])!.value,
    };
  }
}
