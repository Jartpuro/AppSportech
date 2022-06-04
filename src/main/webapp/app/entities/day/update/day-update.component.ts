import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDay, Day } from '../day.model';
import { DayService } from '../service/day.service';
import { State } from 'app/entities/enumerations/state.model';

@Component({
  selector: 'sportechapp-day-update',
  templateUrl: './day-update.component.html',
})
export class DayUpdateComponent implements OnInit {
  isSaving = false;
  stateValues = Object.keys(State);

  editForm = this.fb.group({
    id: [],
    dayName: [null, [Validators.required, Validators.maxLength(40)]],
    dayState: [null, [Validators.required]],
  });

  constructor(protected dayService: DayService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ day }) => {
      this.updateForm(day);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const day = this.createFromForm();
    if (day.id !== undefined) {
      this.subscribeToSaveResponse(this.dayService.update(day));
    } else {
      this.subscribeToSaveResponse(this.dayService.create(day));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDay>>): void {
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

  protected updateForm(day: IDay): void {
    this.editForm.patchValue({
      id: day.id,
      dayName: day.dayName,
      dayState: day.dayState,
    });
  }

  protected createFromForm(): IDay {
    return {
      ...new Day(),
      id: this.editForm.get(['id'])!.value,
      dayName: this.editForm.get(['dayName'])!.value,
      dayState: this.editForm.get(['dayState'])!.value,
    };
  }
}
