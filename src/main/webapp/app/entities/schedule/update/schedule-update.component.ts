import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISchedule, Schedule } from '../schedule.model';
import { ScheduleService } from '../service/schedule.service';
import { IScheduleVersion } from 'app/entities/schedule-version/schedule-version.model';
import { ScheduleVersionService } from 'app/entities/schedule-version/service/schedule-version.service';
import { IModality } from 'app/entities/modality/modality.model';
import { ModalityService } from 'app/entities/modality/service/modality.service';
import { IDay } from 'app/entities/day/day.model';
import { DayService } from 'app/entities/day/service/day.service';
import { ICourseModule } from 'app/entities/course-module/course-module.model';
import { CourseModuleService } from 'app/entities/course-module/service/course-module.service';
import { IClassroom } from 'app/entities/classroom/classroom.model';
import { ClassroomService } from 'app/entities/classroom/service/classroom.service';
import { ITrainer } from 'app/entities/trainer/trainer.model';
import { TrainerService } from 'app/entities/trainer/service/trainer.service';

@Component({
  selector: 'sportechapp-schedule-update',
  templateUrl: './schedule-update.component.html',
})
export class ScheduleUpdateComponent implements OnInit {
  isSaving = false;

  scheduleVersionsSharedCollection: IScheduleVersion[] = [];
  modalitiesSharedCollection: IModality[] = [];
  daysSharedCollection: IDay[] = [];
  courseModulesSharedCollection: ICourseModule[] = [];
  classroomsSharedCollection: IClassroom[] = [];
  trainersSharedCollection: ITrainer[] = [];

  editForm = this.fb.group({
    id: [],
    startTime: [null, [Validators.required]],
    endTime: [null, [Validators.required]],
    scheduleVersion: [null, Validators.required],
    modality: [null, Validators.required],
    day: [null, Validators.required],
    courseModule: [null, Validators.required],
    classroom: [null, Validators.required],
    trainer: [null, Validators.required],
  });

  constructor(
    protected scheduleService: ScheduleService,
    protected scheduleVersionService: ScheduleVersionService,
    protected modalityService: ModalityService,
    protected dayService: DayService,
    protected courseModuleService: CourseModuleService,
    protected classroomService: ClassroomService,
    protected trainerService: TrainerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schedule }) => {
      this.updateForm(schedule);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const schedule = this.createFromForm();
    if (schedule.id !== undefined) {
      this.subscribeToSaveResponse(this.scheduleService.update(schedule));
    } else {
      this.subscribeToSaveResponse(this.scheduleService.create(schedule));
    }
  }

  trackScheduleVersionById(_index: number, item: IScheduleVersion): number {
    return item.id!;
  }

  trackModalityById(_index: number, item: IModality): number {
    return item.id!;
  }

  trackDayById(_index: number, item: IDay): number {
    return item.id!;
  }

  trackCourseModuleById(_index: number, item: ICourseModule): number {
    return item.id!;
  }

  trackClassroomById(_index: number, item: IClassroom): number {
    return item.id!;
  }

  trackTrainerById(_index: number, item: ITrainer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchedule>>): void {
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

  protected updateForm(schedule: ISchedule): void {
    this.editForm.patchValue({
      id: schedule.id,
      startTime: schedule.startTime,
      endTime: schedule.endTime,
      scheduleVersion: schedule.scheduleVersion,
      modality: schedule.modality,
      day: schedule.day,
      courseModule: schedule.courseModule,
      classroom: schedule.classroom,
      trainer: schedule.trainer,
    });

    this.scheduleVersionsSharedCollection = this.scheduleVersionService.addScheduleVersionToCollectionIfMissing(
      this.scheduleVersionsSharedCollection,
      schedule.scheduleVersion
    );
    this.modalitiesSharedCollection = this.modalityService.addModalityToCollectionIfMissing(
      this.modalitiesSharedCollection,
      schedule.modality
    );
    this.daysSharedCollection = this.dayService.addDayToCollectionIfMissing(this.daysSharedCollection, schedule.day);
    this.courseModulesSharedCollection = this.courseModuleService.addCourseModuleToCollectionIfMissing(
      this.courseModulesSharedCollection,
      schedule.courseModule
    );
    this.classroomsSharedCollection = this.classroomService.addClassroomToCollectionIfMissing(
      this.classroomsSharedCollection,
      schedule.classroom
    );
    this.trainersSharedCollection = this.trainerService.addTrainerToCollectionIfMissing(this.trainersSharedCollection, schedule.trainer);
  }

  protected loadRelationshipsOptions(): void {
    this.scheduleVersionService
      .query()
      .pipe(map((res: HttpResponse<IScheduleVersion[]>) => res.body ?? []))
      .pipe(
        map((scheduleVersions: IScheduleVersion[]) =>
          this.scheduleVersionService.addScheduleVersionToCollectionIfMissing(scheduleVersions, this.editForm.get('scheduleVersion')!.value)
        )
      )
      .subscribe((scheduleVersions: IScheduleVersion[]) => (this.scheduleVersionsSharedCollection = scheduleVersions));

    this.modalityService
      .query()
      .pipe(map((res: HttpResponse<IModality[]>) => res.body ?? []))
      .pipe(
        map((modalities: IModality[]) =>
          this.modalityService.addModalityToCollectionIfMissing(modalities, this.editForm.get('modality')!.value)
        )
      )
      .subscribe((modalities: IModality[]) => (this.modalitiesSharedCollection = modalities));

    this.dayService
      .query()
      .pipe(map((res: HttpResponse<IDay[]>) => res.body ?? []))
      .pipe(map((days: IDay[]) => this.dayService.addDayToCollectionIfMissing(days, this.editForm.get('day')!.value)))
      .subscribe((days: IDay[]) => (this.daysSharedCollection = days));

    this.courseModuleService
      .query()
      .pipe(map((res: HttpResponse<ICourseModule[]>) => res.body ?? []))
      .pipe(
        map((courseModules: ICourseModule[]) =>
          this.courseModuleService.addCourseModuleToCollectionIfMissing(courseModules, this.editForm.get('courseModule')!.value)
        )
      )
      .subscribe((courseModules: ICourseModule[]) => (this.courseModulesSharedCollection = courseModules));

    this.classroomService
      .query()
      .pipe(map((res: HttpResponse<IClassroom[]>) => res.body ?? []))
      .pipe(
        map((classrooms: IClassroom[]) =>
          this.classroomService.addClassroomToCollectionIfMissing(classrooms, this.editForm.get('classroom')!.value)
        )
      )
      .subscribe((classrooms: IClassroom[]) => (this.classroomsSharedCollection = classrooms));

    this.trainerService
      .query()
      .pipe(map((res: HttpResponse<ITrainer[]>) => res.body ?? []))
      .pipe(
        map((trainers: ITrainer[]) => this.trainerService.addTrainerToCollectionIfMissing(trainers, this.editForm.get('trainer')!.value))
      )
      .subscribe((trainers: ITrainer[]) => (this.trainersSharedCollection = trainers));
  }

  protected createFromForm(): ISchedule {
    return {
      ...new Schedule(),
      id: this.editForm.get(['id'])!.value,
      startTime: this.editForm.get(['startTime'])!.value,
      endTime: this.editForm.get(['endTime'])!.value,
      scheduleVersion: this.editForm.get(['scheduleVersion'])!.value,
      modality: this.editForm.get(['modality'])!.value,
      day: this.editForm.get(['day'])!.value,
      courseModule: this.editForm.get(['courseModule'])!.value,
      classroom: this.editForm.get(['classroom'])!.value,
      trainer: this.editForm.get(['trainer'])!.value,
    };
  }
}
