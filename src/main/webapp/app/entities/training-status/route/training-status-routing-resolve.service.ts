import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITrainingStatus, TrainingStatus } from '../training-status.model';
import { TrainingStatusService } from '../service/training-status.service';

@Injectable({ providedIn: 'root' })
export class TrainingStatusRoutingResolveService implements Resolve<ITrainingStatus> {
  constructor(protected service: TrainingStatusService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrainingStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((trainingStatus: HttpResponse<TrainingStatus>) => {
          if (trainingStatus.body) {
            return of(trainingStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TrainingStatus());
  }
}
