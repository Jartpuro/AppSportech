import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IClassroomType, ClassroomType } from '../classroom-type.model';
import { ClassroomTypeService } from '../service/classroom-type.service';
import { State } from 'app/entities/enumerations/state.model';

@Component({
  selector: 'sportechapp-classroom-type-update',
  templateUrl: './classroom-type-update.component.html',
})
export class ClassroomTypeUpdateComponent implements OnInit {
  isSaving = false;
  stateValues = Object.keys(State);

  editForm = this.fb.group({
    id: [],
    typeClassroom: [null, [Validators.required, Validators.maxLength(50)]],
    classroomDescription: [null, [Validators.required, Validators.maxLength(100)]],
    classroomState: [null, [Validators.required]],
  });

  constructor(protected classroomTypeService: ClassroomTypeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classroomType }) => {
      this.updateForm(classroomType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const classroomType = this.createFromForm();
    if (classroomType.id !== undefined) {
      this.subscribeToSaveResponse(this.classroomTypeService.update(classroomType));
    } else {
      this.subscribeToSaveResponse(this.classroomTypeService.create(classroomType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClassroomType>>): void {
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

  protected updateForm(classroomType: IClassroomType): void {
    this.editForm.patchValue({
      id: classroomType.id,
      typeClassroom: classroomType.typeClassroom,
      classroomDescription: classroomType.classroomDescription,
      classroomState: classroomType.classroomState,
    });
  }

  protected createFromForm(): IClassroomType {
    return {
      ...new ClassroomType(),
      id: this.editForm.get(['id'])!.value,
      typeClassroom: this.editForm.get(['typeClassroom'])!.value,
      classroomDescription: this.editForm.get(['classroomDescription'])!.value,
      classroomState: this.editForm.get(['classroomState'])!.value,
    };
  }
}
