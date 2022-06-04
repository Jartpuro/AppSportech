import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICourseModule, CourseModule } from '../course-module.model';
import { CourseModuleService } from '../service/course-module.service';

@Injectable({ providedIn: 'root' })
export class CourseModuleRoutingResolveService implements Resolve<ICourseModule> {
  constructor(protected service: CourseModuleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICourseModule> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((courseModule: HttpResponse<CourseModule>) => {
          if (courseModule.body) {
            return of(courseModule.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CourseModule());
  }
}
