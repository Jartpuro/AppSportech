import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IModule, Module } from '../module.model';
import { ModuleService } from '../service/module.service';

@Component({
  selector: 'sportechapp-module-update',
  templateUrl: './module-update.component.html',
})
export class ModuleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    moduleName: [null, [Validators.required]],
    moduleState: [],
  });

  constructor(protected moduleService: ModuleService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ module }) => {
      this.updateForm(module);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const module = this.createFromForm();
    if (module.id !== undefined) {
      this.subscribeToSaveResponse(this.moduleService.update(module));
    } else {
      this.subscribeToSaveResponse(this.moduleService.create(module));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModule>>): void {
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

  protected updateForm(module: IModule): void {
    this.editForm.patchValue({
      id: module.id,
      moduleName: module.moduleName,
      moduleState: module.moduleState,
    });
  }

  protected createFromForm(): IModule {
    return {
      ...new Module(),
      id: this.editForm.get(['id'])!.value,
      moduleName: this.editForm.get(['moduleName'])!.value,
      moduleState: this.editForm.get(['moduleState'])!.value,
    };
  }
}
