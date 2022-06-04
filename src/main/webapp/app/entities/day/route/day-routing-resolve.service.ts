import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDay, Day } from '../day.model';
import { DayService } from '../service/day.service';

@Injectable({ providedIn: 'root' })
export class DayRoutingResolveService implements Resolve<IDay> {
  constructor(protected service: DayService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDay> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((day: HttpResponse<Day>) => {
          if (day.body) {
            return of(day.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Day());
  }
}
