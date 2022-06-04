import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IYear, Year } from '../year.model';
import { YearService } from '../service/year.service';
import { State } from 'app/entities/enumerations/state.model';

@Component({
  selector: 'sportechapp-year-update',
  templateUrl: './year-update.component.html',
})
export class YearUpdateComponent implements OnInit {
  isSaving = false;
  stateValues = Object.keys(State);

  editForm = this.fb.group({
    id: [],
    yearNumber: [null, [Validators.required]],
    yearState: [null, [Validators.required]],
  });

  constructor(protected yearService: YearService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ year }) => {
      this.updateForm(year);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const year = this.createFromForm();
    if (year.id !== undefined) {
      this.subscribeToSaveResponse(this.yearService.update(year));
    } else {
      this.subscribeToSaveResponse(this.yearService.create(year));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IYear>>): void {
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

  protected updateForm(year: IYear): void {
    this.editForm.patchValue({
      id: year.id,
      yearNumber: year.yearNumber,
      yearState: year.yearState,
    });
  }

  protected createFromForm(): IYear {
    return {
      ...new Year(),
      id: this.editForm.get(['id'])!.value,
      yearNumber: this.editForm.get(['yearNumber'])!.value,
      yearState: this.editForm.get(['yearState'])!.value,
    };
  }
}
