import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBoundingSchedule, BoundingSchedule } from '../bounding-schedule.model';
import { BoundingScheduleService } from '../service/bounding-schedule.service';

@Injectable({ providedIn: 'root' })
export class BoundingScheduleRoutingResolveService implements Resolve<IBoundingSchedule> {
  constructor(protected service: BoundingScheduleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBoundingSchedule> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((boundingSchedule: HttpResponse<BoundingSchedule>) => {
          if (boundingSchedule.body) {
            return of(boundingSchedule.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BoundingSchedule());
  }
}
