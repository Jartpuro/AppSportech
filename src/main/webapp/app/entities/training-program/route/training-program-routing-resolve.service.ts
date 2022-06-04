import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITrainingProgram, TrainingProgram } from '../training-program.model';
import { TrainingProgramService } from '../service/training-program.service';

@Injectable({ providedIn: 'root' })
export class TrainingProgramRoutingResolveService implements Resolve<ITrainingProgram> {
  constructor(protected service: TrainingProgramService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrainingProgram> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((trainingProgram: HttpResponse<TrainingProgram>) => {
          if (trainingProgram.body) {
            return of(trainingProgram.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TrainingProgram());
  }
}
