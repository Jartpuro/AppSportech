import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBondingTrainer, getBondingTrainerIdentifier } from '../bonding-trainer.model';

export type EntityResponseType = HttpResponse<IBondingTrainer>;
export type EntityArrayResponseType = HttpResponse<IBondingTrainer[]>;

@Injectable({ providedIn: 'root' })
export class BondingTrainerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bonding-trainers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bondingTrainer: IBondingTrainer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bondingTrainer);
    return this.http
      .post<IBondingTrainer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bondingTrainer: IBondingTrainer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bondingTrainer);
    return this.http
      .put<IBondingTrainer>(`${this.resourceUrl}/${getBondingTrainerIdentifier(bondingTrainer) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(bondingTrainer: IBondingTrainer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bondingTrainer);
    return this.http
      .patch<IBondingTrainer>(`${this.resourceUrl}/${getBondingTrainerIdentifier(bondingTrainer) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBondingTrainer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBondingTrainer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBondingTrainerToCollectionIfMissing(
    bondingTrainerCollection: IBondingTrainer[],
    ...bondingTrainersToCheck: (IBondingTrainer | null | undefined)[]
  ): IBondingTrainer[] {
    const bondingTrainers: IBondingTrainer[] = bondingTrainersToCheck.filter(isPresent);
    if (bondingTrainers.length > 0) {
      const bondingTrainerCollectionIdentifiers = bondingTrainerCollection.map(
        bondingTrainerItem => getBondingTrainerIdentifier(bondingTrainerItem)!
      );
      const bondingTrainersToAdd = bondingTrainers.filter(bondingTrainerItem => {
        const bondingTrainerIdentifier = getBondingTrainerIdentifier(bondingTrainerItem);
        if (bondingTrainerIdentifier == null || bondingTrainerCollectionIdentifiers.includes(bondingTrainerIdentifier)) {
          return false;
        }
        bondingTrainerCollectionIdentifiers.push(bondingTrainerIdentifier);
        return true;
      });
      return [...bondingTrainersToAdd, ...bondingTrainerCollection];
    }
    return bondingTrainerCollection;
  }

  protected convertDateFromClient(bondingTrainer: IBondingTrainer): IBondingTrainer {
    return Object.assign({}, bondingTrainer, {
      startTime: bondingTrainer.startTime?.isValid() ? bondingTrainer.startTime.format(DATE_FORMAT) : undefined,
      endTime: bondingTrainer.endTime?.isValid() ? bondingTrainer.endTime.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startTime = res.body.startTime ? dayjs(res.body.startTime) : undefined;
      res.body.endTime = res.body.endTime ? dayjs(res.body.endTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((bondingTrainer: IBondingTrainer) => {
        bondingTrainer.startTime = bondingTrainer.startTime ? dayjs(bondingTrainer.startTime) : undefined;
        bondingTrainer.endTime = bondingTrainer.endTime ? dayjs(bondingTrainer.endTime) : undefined;
      });
    }
    return res;
  }
}
