import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICampus, Campus } from '../campus.model';
import { CampusService } from '../service/campus.service';
import { State } from 'app/entities/enumerations/state.model';

@Component({
  selector: 'sportechapp-campus-update',
  templateUrl: './campus-update.component.html',
})
export class CampusUpdateComponent implements OnInit {
  isSaving = false;
  stateValues = Object.keys(State);

  editForm = this.fb.group({
    id: [],
    campusName: [null, [Validators.required, Validators.maxLength(50)]],
    campusAddress: [null, [Validators.required, Validators.maxLength(400)]],
    campusState: [null, [Validators.required]],
  });

  constructor(protected campusService: CampusService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ campus }) => {
      this.updateForm(campus);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const campus = this.createFromForm();
    if (campus.id !== undefined) {
      this.subscribeToSaveResponse(this.campusService.update(campus));
    } else {
      this.subscribeToSaveResponse(this.campusService.create(campus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICampus>>): void {
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

  protected updateForm(campus: ICampus): void {
    this.editForm.patchValue({
      id: campus.id,
      campusName: campus.campusName,
      campusAddress: campus.campusAddress,
      campusState: campus.campusState,
    });
  }

  protected createFromForm(): ICampus {
    return {
      ...new Campus(),
      id: this.editForm.get(['id'])!.value,
      campusName: this.editForm.get(['campusName'])!.value,
      campusAddress: this.editForm.get(['campusAddress'])!.value,
      campusState: this.editForm.get(['campusState'])!.value,
    };
  }
}
