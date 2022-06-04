import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICourseStatus, CourseStatus } from '../course-status.model';
import { CourseStatusService } from '../service/course-status.service';

@Injectable({ providedIn: 'root' })
export class CourseStatusRoutingResolveService implements Resolve<ICourseStatus> {
  constructor(protected service: CourseStatusService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICourseStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((courseStatus: HttpResponse<CourseStatus>) => {
          if (courseStatus.body) {
            return of(courseStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CourseStatus());
  }
}
