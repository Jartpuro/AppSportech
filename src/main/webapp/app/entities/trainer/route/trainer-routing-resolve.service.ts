import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITrainer, Trainer } from '../trainer.model';
import { TrainerService } from '../service/trainer.service';

@Injectable({ providedIn: 'root' })
export class TrainerRoutingResolveService implements Resolve<ITrainer> {
  constructor(protected service: TrainerService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrainer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((trainer: HttpResponse<Trainer>) => {
          if (trainer.body) {
            return of(trainer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Trainer());
  }
}
