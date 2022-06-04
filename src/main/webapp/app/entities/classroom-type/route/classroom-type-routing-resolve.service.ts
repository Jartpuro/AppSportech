import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IClassroomType, ClassroomType } from '../classroom-type.model';
import { ClassroomTypeService } from '../service/classroom-type.service';

@Injectable({ providedIn: 'root' })
export class ClassroomTypeRoutingResolveService implements Resolve<IClassroomType> {
  constructor(protected service: ClassroomTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IClassroomType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((classroomType: HttpResponse<ClassroomType>) => {
          if (classroomType.body) {
            return of(classroomType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ClassroomType());
  }
}
