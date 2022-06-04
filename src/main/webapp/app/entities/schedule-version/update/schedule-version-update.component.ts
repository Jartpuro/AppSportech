import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IScheduleVersion, ScheduleVersion } from '../schedule-version.model';
import { ScheduleVersionService } from '../service/schedule-version.service';
import { State } from 'app/entities/enumerations/state.model';

@Component({
  selector: 'sportechapp-schedule-version-update',
  templateUrl: './schedule-version-update.component.html',
})
export class ScheduleVersionUpdateComponent implements OnInit {
  isSaving = false;
  stateValues = Object.keys(State);

  editForm = this.fb.group({
    id: [],
    versionNumber: [null, [Validators.required, Validators.maxLength(40)]],
    versionState: [null, [Validators.required]],
  });

  constructor(
    protected scheduleVersionService: ScheduleVersionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scheduleVersion }) => {
      this.updateForm(scheduleVersion);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const scheduleVersion = this.createFromForm();
    if (scheduleVersion.id !== undefined) {
      this.subscribeToSaveResponse(this.scheduleVersionService.update(scheduleVersion));
    } else {
      this.subscribeToSaveResponse(this.scheduleVersionService.create(scheduleVersion));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScheduleVersion>>): void {
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

  protected updateForm(scheduleVersion: IScheduleVersion): void {
    this.editForm.patchValue({
      id: scheduleVersion.id,
      versionNumber: scheduleVersion.versionNumber,
      versionState: scheduleVersion.versionState,
    });
  }

  protected createFromForm(): IScheduleVersion {
    return {
      ...new ScheduleVersion(),
      id: this.editForm.get(['id'])!.value,
      versionNumber: this.editForm.get(['versionNumber'])!.value,
      versionState: this.editForm.get(['versionState'])!.value,
    };
  }
}
