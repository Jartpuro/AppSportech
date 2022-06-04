import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITrainee, Trainee } from '../trainee.model';
import { TraineeService } from '../service/trainee.service';

@Injectable({ providedIn: 'root' })
export class TraineeRoutingResolveService implements Resolve<ITrainee> {
  constructor(protected service: TraineeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrainee> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((trainee: HttpResponse<Trainee>) => {
          if (trainee.body) {
            return of(trainee.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Trainee());
  }
}
