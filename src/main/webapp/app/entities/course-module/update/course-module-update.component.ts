import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICourseModule, CourseModule } from '../course-module.model';
import { CourseModuleService } from '../service/course-module.service';
import { ICourse } from 'app/entities/course/course.model';
import { CourseService } from 'app/entities/course/service/course.service';
import { IModule } from 'app/entities/module/module.model';
import { ModuleService } from 'app/entities/module/service/module.service';

@Component({
  selector: 'sportechapp-course-module-update',
  templateUrl: './course-module-update.component.html',
})
export class CourseModuleUpdateComponent implements OnInit {
  isSaving = false;

  coursesSharedCollection: ICourse[] = [];
  modulesSharedCollection: IModule[] = [];

  editForm = this.fb.group({
    id: [],
    course: [null, Validators.required],
    module: [null, Validators.required],
  });

  constructor(
    protected courseModuleService: CourseModuleService,
    protected courseService: CourseService,
    protected moduleService: ModuleService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courseModule }) => {
      this.updateForm(courseModule);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const courseModule = this.createFromForm();
    if (courseModule.id !== undefined) {
      this.subscribeToSaveResponse(this.courseModuleService.update(courseModule));
    } else {
      this.subscribeToSaveResponse(this.courseModuleService.create(courseModule));
    }
  }

  trackCourseById(_index: number, item: ICourse): number {
    return item.id!;
  }

  trackModuleById(_index: number, item: IModule): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourseModule>>): void {
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

  protected updateForm(courseModule: ICourseModule): void {
    this.editForm.patchValue({
      id: courseModule.id,
      course: courseModule.course,
      module: courseModule.module,
    });

    this.coursesSharedCollection = this.courseService.addCourseToCollectionIfMissing(this.coursesSharedCollection, courseModule.course);
    this.modulesSharedCollection = this.moduleService.addModuleToCollectionIfMissing(this.modulesSharedCollection, courseModule.module);
  }

  protected loadRelationshipsOptions(): void {
    this.courseService
      .query()
      .pipe(map((res: HttpResponse<ICourse[]>) => res.body ?? []))
      .pipe(map((courses: ICourse[]) => this.courseService.addCourseToCollectionIfMissing(courses, this.editForm.get('course')!.value)))
      .subscribe((courses: ICourse[]) => (this.coursesSharedCollection = courses));

    this.moduleService
      .query()
      .pipe(map((res: HttpResponse<IModule[]>) => res.body ?? []))
      .pipe(map((modules: IModule[]) => this.moduleService.addModuleToCollectionIfMissing(modules, this.editForm.get('module')!.value)))
      .subscribe((modules: IModule[]) => (this.modulesSharedCollection = modules));
  }

  protected createFromForm(): ICourseModule {
    return {
      ...new CourseModule(),
      id: this.editForm.get(['id'])!.value,
      course: this.editForm.get(['course'])!.value,
      module: this.editForm.get(['module'])!.value,
    };
  }
}
