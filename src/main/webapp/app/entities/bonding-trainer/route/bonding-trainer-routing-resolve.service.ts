import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBondingTrainer, BondingTrainer } from '../bonding-trainer.model';
import { BondingTrainerService } from '../service/bonding-trainer.service';

@Injectable({ providedIn: 'root' })
export class BondingTrainerRoutingResolveService implements Resolve<IBondingTrainer> {
  constructor(protected service: BondingTrainerService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBondingTrainer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bondingTrainer: HttpResponse<BondingTrainer>) => {
          if (bondingTrainer.body) {
            return of(bondingTrainer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BondingTrainer());
  }
}
