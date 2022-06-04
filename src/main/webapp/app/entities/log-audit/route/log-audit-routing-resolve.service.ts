import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILogAudit, LogAudit } from '../log-audit.model';
import { LogAuditService } from '../service/log-audit.service';

@Injectable({ providedIn: 'root' })
export class LogAuditRoutingResolveService implements Resolve<ILogAudit> {
  constructor(protected service: LogAuditService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILogAudit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((logAudit: HttpResponse<LogAudit>) => {
          if (logAudit.body) {
            return of(logAudit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LogAudit());
  }
}
