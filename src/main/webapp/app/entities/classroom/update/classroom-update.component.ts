import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IClassroom, Classroom } from '../classroom.model';
import { ClassroomService } from '../service/classroom.service';
import { IClassroomType } from 'app/entities/classroom-type/classroom-type.model';
import { ClassroomTypeService } from 'app/entities/classroom-type/service/classroom-type.service';
import { ICampus } from 'app/entities/campus/campus.model';
import { CampusService } from 'app/entities/campus/service/campus.service';

@Component({
  selector: 'sportechapp-classroom-update',
  templateUrl: './classroom-update.component.html',
})
export class ClassroomUpdateComponent implements OnInit {
  isSaving = false;

  classroomTypesSharedCollection: IClassroomType[] = [];
  campusesSharedCollection: ICampus[] = [];

  editForm = this.fb.group({
    id: [],
    classroomNumber: [null, [Validators.required, Validators.maxLength(50)]],
    classroomDescription: [null, [Validators.required, Validators.maxLength(1000)]],
    classroomState: [null, [Validators.required, Validators.maxLength(40)]],
    classroomType: [null, Validators.required],
    campus: [null, Validators.required],
  });

  constructor(
    protected classroomService: ClassroomService,
    protected classroomTypeService: ClassroomTypeService,
    protected campusService: CampusService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classroom }) => {
      this.updateForm(classroom);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const classroom = this.createFromForm();
    if (classroom.id !== undefined) {
      this.subscribeToSaveResponse(this.classroomService.update(classroom));
    } else {
      this.subscribeToSaveResponse(this.classroomService.create(classroom));
    }
  }

  trackClassroomTypeById(_index: number, item: IClassroomType): number {
    return item.id!;
  }

  trackCampusById(_index: number, item: ICampus): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClassroom>>): void {
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

  protected updateForm(classroom: IClassroom): void {
    this.editForm.patchValue({
      id: classroom.id,
      classroomNumber: classroom.classroomNumber,
      classroomDescription: classroom.classroomDescription,
      classroomState: classroom.classroomState,
      classroomType: classroom.classroomType,
      campus: classroom.campus,
    });

    this.classroomTypesSharedCollection = this.classroomTypeService.addClassroomTypeToCollectionIfMissing(
      this.classroomTypesSharedCollection,
      classroom.classroomType
    );
    this.campusesSharedCollection = this.campusService.addCampusToCollectionIfMissing(this.campusesSharedCollection, classroom.campus);
  }

  protected loadRelationshipsOptions(): void {
    this.classroomTypeService
      .query()
      .pipe(map((res: HttpResponse<IClassroomType[]>) => res.body ?? []))
      .pipe(
        map((classroomTypes: IClassroomType[]) =>
          this.classroomTypeService.addClassroomTypeToCollectionIfMissing(classroomTypes, this.editForm.get('classroomType')!.value)
        )
      )
      .subscribe((classroomTypes: IClassroomType[]) => (this.classroomTypesSharedCollection = classroomTypes));

    this.campusService
      .query()
      .pipe(map((res: HttpResponse<ICampus[]>) => res.body ?? []))
      .pipe(map((campuses: ICampus[]) => this.campusService.addCampusToCollectionIfMissing(campuses, this.editForm.get('campus')!.value)))
      .subscribe((campuses: ICampus[]) => (this.campusesSharedCollection = campuses));
  }

  protected createFromForm(): IClassroom {
    return {
      ...new Classroom(),
      id: this.editForm.get(['id'])!.value,
      classroomNumber: this.editForm.get(['classroomNumber'])!.value,
      classroomDescription: this.editForm.get(['classroomDescription'])!.value,
      classroomState: this.editForm.get(['classroomState'])!.value,
      classroomType: this.editForm.get(['classroomType'])!.value,
      campus: this.editForm.get(['campus'])!.value,
    };
  }
}
