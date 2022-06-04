import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IModality, Modality } from '../modality.model';
import { ModalityService } from '../service/modality.service';

@Injectable({ providedIn: 'root' })
export class ModalityRoutingResolveService implements Resolve<IModality> {
  constructor(protected service: ModalityService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IModality> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((modality: HttpResponse<Modality>) => {
          if (modality.body) {
            return of(modality.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Modality());
  }
}
