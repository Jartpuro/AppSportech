import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICampus, Campus } from '../campus.model';
import { CampusService } from '../service/campus.service';

@Injectable({ providedIn: 'root' })
export class CampusRoutingResolveService implements Resolve<ICampus> {
  constructor(protected service: CampusService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICampus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((campus: HttpResponse<Campus>) => {
          if (campus.body) {
            return of(campus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Campus());
  }
}
