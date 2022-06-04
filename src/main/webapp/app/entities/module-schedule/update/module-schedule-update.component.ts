import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IModuleSchedule, ModuleSchedule } from '../module-schedule.model';
import { ModuleScheduleService } from '../service/module-schedule.service';
import { IModule } from 'app/entities/module/module.model';
import { ModuleService } from 'app/entities/module/service/module.service';

@Component({
  selector: 'sportechapp-module-schedule-update',
  templateUrl: './module-schedule-update.component.html',
})
export class ModuleScheduleUpdateComponent implements OnInit {
  isSaving = false;

  modulesSharedCollection: IModule[] = [];

  editForm = this.fb.group({
    id: [],
    module: [null, Validators.required],
  });

  constructor(
    protected moduleScheduleService: ModuleScheduleService,
    protected moduleService: ModuleService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ moduleSchedule }) => {
      this.updateForm(moduleSchedule);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const moduleSchedule = this.createFromForm();
    if (moduleSchedule.id !== undefined) {
      this.subscribeToSaveResponse(this.moduleScheduleService.update(moduleSchedule));
    } else {
      this.subscribeToSaveResponse(this.moduleScheduleService.create(moduleSchedule));
    }
  }

  trackModuleById(_index: number, item: IModule): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModuleSchedule>>): void {
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

  protected updateForm(moduleSchedule: IModuleSchedule): void {
    this.editForm.patchValue({
      id: moduleSchedule.id,
      module: moduleSchedule.module,
    });

    this.modulesSharedCollection = this.moduleService.addModuleToCollectionIfMissing(this.modulesSharedCollection, moduleSchedule.module);
  }

  protected loadRelationshipsOptions(): void {
    this.moduleService
      .query()
      .pipe(map((res: HttpResponse<IModule[]>) => res.body ?? []))
      .pipe(map((modules: IModule[]) => this.moduleService.addModuleToCollectionIfMissing(modules, this.editForm.get('module')!.value)))
      .subscribe((modules: IModule[]) => (this.modulesSharedCollection = modules));
  }

  protected createFromForm(): IModuleSchedule {
    return {
      ...new ModuleSchedule(),
      id: this.editForm.get(['id'])!.value,
      module: this.editForm.get(['module'])!.value,
    };
  }
}
