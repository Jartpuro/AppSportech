import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IBonding, Bonding } from '../bonding.model';
import { BondingService } from '../service/bonding.service';
import { State } from 'app/entities/enumerations/state.model';

@Component({
  selector: 'sportechapp-bonding-update',
  templateUrl: './bonding-update.component.html',
})
export class BondingUpdateComponent implements OnInit {
  isSaving = false;
  stateValues = Object.keys(State);

  editForm = this.fb.group({
    id: [],
    bondingType: [null, [Validators.required, Validators.maxLength(40)]],
    workingHours: [null, [Validators.required]],
    bondingState: [null, [Validators.required]],
  });

  constructor(protected bondingService: BondingService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bonding }) => {
      this.updateForm(bonding);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bonding = this.createFromForm();
    if (bonding.id !== undefined) {
      this.subscribeToSaveResponse(this.bondingService.update(bonding));
    } else {
      this.subscribeToSaveResponse(this.bondingService.create(bonding));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBonding>>): void {
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

  protected updateForm(bonding: IBonding): void {
    this.editForm.patchValue({
      id: bonding.id,
      bondingType: bonding.bondingType,
      workingHours: bonding.workingHours,
      bondingState: bonding.bondingState,
    });
  }

  protected createFromForm(): IBonding {
    return {
      ...new Bonding(),
      id: this.editForm.get(['id'])!.value,
      bondingType: this.editForm.get(['bondingType'])!.value,
      workingHours: this.editForm.get(['workingHours'])!.value,
      bondingState: this.editForm.get(['bondingState'])!.value,
    };
  }
}
