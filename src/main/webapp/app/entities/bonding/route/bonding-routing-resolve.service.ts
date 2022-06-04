import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBonding, Bonding } from '../bonding.model';
import { BondingService } from '../service/bonding.service';

@Injectable({ providedIn: 'root' })
export class BondingRoutingResolveService implements Resolve<IBonding> {
  constructor(protected service: BondingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBonding> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bonding: HttpResponse<Bonding>) => {
          if (bonding.body) {
            return of(bonding.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Bonding());
  }
}
