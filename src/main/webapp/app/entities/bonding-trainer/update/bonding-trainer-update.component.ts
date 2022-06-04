import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IBondingTrainer, BondingTrainer } from '../bonding-trainer.model';
import { BondingTrainerService } from '../service/bonding-trainer.service';
import { IYear } from 'app/entities/year/year.model';
import { YearService } from 'app/entities/year/service/year.service';
import { ITrainer } from 'app/entities/trainer/trainer.model';
import { TrainerService } from 'app/entities/trainer/service/trainer.service';
import { IBonding } from 'app/entities/bonding/bonding.model';
import { BondingService } from 'app/entities/bonding/service/bonding.service';

@Component({
  selector: 'sportechapp-bonding-trainer-update',
  templateUrl: './bonding-trainer-update.component.html',
})
export class BondingTrainerUpdateComponent implements OnInit {
  isSaving = false;

  yearsSharedCollection: IYear[] = [];
  trainersSharedCollection: ITrainer[] = [];
  bondingsSharedCollection: IBonding[] = [];

  editForm = this.fb.group({
    id: [],
    startTime: [null, [Validators.required]],
    endTime: [null, [Validators.required]],
    year: [null, Validators.required],
    trainer: [null, Validators.required],
    bonding: [null, Validators.required],
  });

  constructor(
    protected bondingTrainerService: BondingTrainerService,
    protected yearService: YearService,
    protected trainerService: TrainerService,
    protected bondingService: BondingService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bondingTrainer }) => {
      this.updateForm(bondingTrainer);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bondingTrainer = this.createFromForm();
    if (bondingTrainer.id !== undefined) {
      this.subscribeToSaveResponse(this.bondingTrainerService.update(bondingTrainer));
    } else {
      this.subscribeToSaveResponse(this.bondingTrainerService.create(bondingTrainer));
    }
  }

  trackYearById(_index: number, item: IYear): number {
    return item.id!;
  }

  trackTrainerById(_index: number, item: ITrainer): number {
    return item.id!;
  }

  trackBondingById(_index: number, item: IBonding): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBondingTrainer>>): void {
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

  protected updateForm(bondingTrainer: IBondingTrainer): void {
    this.editForm.patchValue({
      id: bondingTrainer.id,
      startTime: bondingTrainer.startTime,
      endTime: bondingTrainer.endTime,
      year: bondingTrainer.year,
      trainer: bondingTrainer.trainer,
      bonding: bondingTrainer.bonding,
    });

    this.yearsSharedCollection = this.yearService.addYearToCollectionIfMissing(this.yearsSharedCollection, bondingTrainer.year);
    this.trainersSharedCollection = this.trainerService.addTrainerToCollectionIfMissing(
      this.trainersSharedCollection,
      bondingTrainer.trainer
    );
    this.bondingsSharedCollection = this.bondingService.addBondingToCollectionIfMissing(
      this.bondingsSharedCollection,
      bondingTrainer.bonding
    );
  }

  protected loadRelationshipsOptions(): void {
    this.yearService
      .query()
      .pipe(map((res: HttpResponse<IYear[]>) => res.body ?? []))
      .pipe(map((years: IYear[]) => this.yearService.addYearToCollectionIfMissing(years, this.editForm.get('year')!.value)))
      .subscribe((years: IYear[]) => (this.yearsSharedCollection = years));

    this.trainerService
      .query()
      .pipe(map((res: HttpResponse<ITrainer[]>) => res.body ?? []))
      .pipe(
        map((trainers: ITrainer[]) => this.trainerService.addTrainerToCollectionIfMissing(trainers, this.editForm.get('trainer')!.value))
      )
      .subscribe((trainers: ITrainer[]) => (this.trainersSharedCollection = trainers));

    this.bondingService
      .query()
      .pipe(map((res: HttpResponse<IBonding[]>) => res.body ?? []))
      .pipe(
        map((bondings: IBonding[]) => this.bondingService.addBondingToCollectionIfMissing(bondings, this.editForm.get('bonding')!.value))
      )
      .subscribe((bondings: IBonding[]) => (this.bondingsSharedCollection = bondings));
  }

  protected createFromForm(): IBondingTrainer {
    return {
      ...new BondingTrainer(),
      id: this.editForm.get(['id'])!.value,
      startTime: this.editForm.get(['startTime'])!.value,
      endTime: this.editForm.get(['endTime'])!.value,
      year: this.editForm.get(['year'])!.value,
      trainer: this.editForm.get(['trainer'])!.value,
      bonding: this.editForm.get(['bonding'])!.value,
    };
  }
}
