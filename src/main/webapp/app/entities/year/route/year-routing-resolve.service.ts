import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IYear, Year } from '../year.model';
import { YearService } from '../service/year.service';

@Injectable({ providedIn: 'root' })
export class YearRoutingResolveService implements Resolve<IYear> {
  constructor(protected service: YearService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IYear> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((year: HttpResponse<Year>) => {
          if (year.body) {
            return of(year.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Year());
  }
}
