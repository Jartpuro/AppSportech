import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IScheduleVersion, ScheduleVersion } from '../schedule-version.model';
import { ScheduleVersionService } from '../service/schedule-version.service';

@Injectable({ providedIn: 'root' })
export class ScheduleVersionRoutingResolveService implements Resolve<IScheduleVersion> {
  constructor(protected service: ScheduleVersionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IScheduleVersion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((scheduleVersion: HttpResponse<ScheduleVersion>) => {
          if (scheduleVersion.body) {
            return of(scheduleVersion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ScheduleVersion());
  }
}
