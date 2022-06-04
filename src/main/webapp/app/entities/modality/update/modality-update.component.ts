import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IModality, Modality } from '../modality.model';
import { ModalityService } from '../service/modality.service';
import { State } from 'app/entities/enumerations/state.model';

@Component({
  selector: 'sportechapp-modality-update',
  templateUrl: './modality-update.component.html',
})
export class ModalityUpdateComponent implements OnInit {
  isSaving = false;
  stateValues = Object.keys(State);

  editForm = this.fb.group({
    id: [],
    modalityName: [null, [Validators.required, Validators.maxLength(40)]],
    modalityColor: [null, [Validators.required, Validators.maxLength(50)]],
    modalityState: [null, [Validators.required]],
  });

  constructor(protected modalityService: ModalityService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modality }) => {
      this.updateForm(modality);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const modality = this.createFromForm();
    if (modality.id !== undefined) {
      this.subscribeToSaveResponse(this.modalityService.update(modality));
    } else {
      this.subscribeToSaveResponse(this.modalityService.create(modality));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModality>>): void {
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

  protected updateForm(modality: IModality): void {
    this.editForm.patchValue({
      id: modality.id,
      modalityName: modality.modalityName,
      modalityColor: modality.modalityColor,
      modalityState: modality.modalityState,
    });
  }

  protected createFromForm(): IModality {
    return {
      ...new Modality(),
      id: this.editForm.get(['id'])!.value,
      modalityName: this.editForm.get(['modalityName'])!.value,
      modalityColor: this.editForm.get(['modalityColor'])!.value,
      modalityState: this.editForm.get(['modalityState'])!.value,
    };
  }
}
