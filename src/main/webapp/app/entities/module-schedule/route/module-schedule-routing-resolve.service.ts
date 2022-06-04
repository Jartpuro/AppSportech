import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IModuleSchedule, ModuleSchedule } from '../module-schedule.model';
import { ModuleScheduleService } from '../service/module-schedule.service';

@Injectable({ providedIn: 'root' })
export class ModuleScheduleRoutingResolveService implements Resolve<IModuleSchedule> {
  constructor(protected service: ModuleScheduleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IModuleSchedule> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((moduleSchedule: HttpResponse<ModuleSchedule>) => {
          if (moduleSchedule.body) {
            return of(moduleSchedule.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ModuleSchedule());
  }
}
