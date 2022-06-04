import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILogError, LogError } from '../log-error.model';
import { LogErrorService } from '../service/log-error.service';

@Injectable({ providedIn: 'root' })
export class LogErrorRoutingResolveService implements Resolve<ILogError> {
  constructor(protected service: LogErrorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILogError> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((logError: HttpResponse<LogError>) => {
          if (logError.body) {
            return of(logError.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LogError());
  }
}
