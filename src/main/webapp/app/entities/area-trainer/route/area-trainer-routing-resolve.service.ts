import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAreaTrainer, AreaTrainer } from '../area-trainer.model';
import { AreaTrainerService } from '../service/area-trainer.service';

@Injectable({ providedIn: 'root' })
export class AreaTrainerRoutingResolveService implements Resolve<IAreaTrainer> {
  constructor(protected service: AreaTrainerService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAreaTrainer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((areaTrainer: HttpResponse<AreaTrainer>) => {
          if (areaTrainer.body) {
            return of(areaTrainer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AreaTrainer());
  }
}
