import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'document-type',
        data: { pageTitle: 'sportechappApp.documentType.home.title' },
        loadChildren: () => import('./document-type/document-type.module').then(m => m.DocumentTypeModule),
      },
      {
        path: 'customer',
        data: { pageTitle: 'sportechappApp.customer.home.title' },
        loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule),
      },
      {
        path: 'training-status',
        data: { pageTitle: 'sportechappApp.trainingStatus.home.title' },
        loadChildren: () => import('./training-status/training-status.module').then(m => m.TrainingStatusModule),
      },
      {
        path: 'course-status',
        data: { pageTitle: 'sportechappApp.courseStatus.home.title' },
        loadChildren: () => import('./course-status/course-status.module').then(m => m.CourseStatusModule),
      },
      {
        path: 'course',
        data: { pageTitle: 'sportechappApp.course.home.title' },
        loadChildren: () => import('./course/course.module').then(m => m.CourseModule),
      },
      {
        path: 'trainee',
        data: { pageTitle: 'sportechappApp.trainee.home.title' },
        loadChildren: () => import('./trainee/trainee.module').then(m => m.TraineeModule),
      },
      {
        path: 'log-error',
        data: { pageTitle: 'sportechappApp.logError.home.title' },
        loadChildren: () => import('./log-error/log-error.module').then(m => m.LogErrorModule),
      },
      {
        path: 'log-audit',
        data: { pageTitle: 'sportechappApp.logAudit.home.title' },
        loadChildren: () => import('./log-audit/log-audit.module').then(m => m.LogAuditModule),
      },
      {
        path: 'module-schedule',
        data: { pageTitle: 'sportechappApp.moduleSchedule.home.title' },
        loadChildren: () => import('./module-schedule/module-schedule.module').then(m => m.ModuleScheduleModule),
      },
      {
        path: 'training-program',
        data: { pageTitle: 'sportechappApp.trainingProgram.home.title' },
        loadChildren: () => import('./training-program/training-program.module').then(m => m.TrainingProgramModule),
      },
      {
        path: 'module',
        data: { pageTitle: 'sportechappApp.module.home.title' },
        loadChildren: () => import('./module/module.module').then(m => m.ModuleModule),
      },
      {
        path: 'course-module',
        data: { pageTitle: 'sportechappApp.courseModule.home.title' },
        loadChildren: () => import('./course-module/course-module.module').then(m => m.CourseModuleModule),
      },
      {
        path: 'classroom-type',
        data: { pageTitle: 'sportechappApp.classroomType.home.title' },
        loadChildren: () => import('./classroom-type/classroom-type.module').then(m => m.ClassroomTypeModule),
      },
      {
        path: 'campus',
        data: { pageTitle: 'sportechappApp.campus.home.title' },
        loadChildren: () => import('./campus/campus.module').then(m => m.CampusModule),
      },
      {
        path: 'classroom',
        data: { pageTitle: 'sportechappApp.classroom.home.title' },
        loadChildren: () => import('./classroom/classroom.module').then(m => m.ClassroomModule),
      },
      {
        path: 'day',
        data: { pageTitle: 'sportechappApp.day.home.title' },
        loadChildren: () => import('./day/day.module').then(m => m.DayModule),
      },
      {
        path: 'modality',
        data: { pageTitle: 'sportechappApp.modality.home.title' },
        loadChildren: () => import('./modality/modality.module').then(m => m.ModalityModule),
      },
      {
        path: 'schedule-version',
        data: { pageTitle: 'sportechappApp.scheduleVersion.home.title' },
        loadChildren: () => import('./schedule-version/schedule-version.module').then(m => m.ScheduleVersionModule),
      },
      {
        path: 'schedule',
        data: { pageTitle: 'sportechappApp.schedule.home.title' },
        loadChildren: () => import('./schedule/schedule.module').then(m => m.ScheduleModule),
      },
      {
        path: 'year',
        data: { pageTitle: 'sportechappApp.year.home.title' },
        loadChildren: () => import('./year/year.module').then(m => m.YearModule),
      },
      {
        path: 'area',
        data: { pageTitle: 'sportechappApp.area.home.title' },
        loadChildren: () => import('./area/area.module').then(m => m.AreaModule),
      },
      {
        path: 'bonding',
        data: { pageTitle: 'sportechappApp.bonding.home.title' },
        loadChildren: () => import('./bonding/bonding.module').then(m => m.BondingModule),
      },
      {
        path: 'trainer',
        data: { pageTitle: 'sportechappApp.trainer.home.title' },
        loadChildren: () => import('./trainer/trainer.module').then(m => m.TrainerModule),
      },
      {
        path: 'area-trainer',
        data: { pageTitle: 'sportechappApp.areaTrainer.home.title' },
        loadChildren: () => import('./area-trainer/area-trainer.module').then(m => m.AreaTrainerModule),
      },
      {
        path: 'bonding-trainer',
        data: { pageTitle: 'sportechappApp.bondingTrainer.home.title' },
        loadChildren: () => import('./bonding-trainer/bonding-trainer.module').then(m => m.BondingTrainerModule),
      },
      {
        path: 'bounding-schedule',
        data: { pageTitle: 'sportechappApp.boundingSchedule.home.title' },
        loadChildren: () => import('./bounding-schedule/bounding-schedule.module').then(m => m.BoundingScheduleModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
