import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAreaTrainer, getAreaTrainerIdentifier } from '../area-trainer.model';

export type EntityResponseType = HttpResponse<IAreaTrainer>;
export type EntityArrayResponseType = HttpResponse<IAreaTrainer[]>;

@Injectable({ providedIn: 'root' })
export class AreaTrainerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/area-trainers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(areaTrainer: IAreaTrainer): Observable<EntityResponseType> {
    return this.http.post<IAreaTrainer>(this.resourceUrl, areaTrainer, { observe: 'response' });
  }

  update(areaTrainer: IAreaTrainer): Observable<EntityResponseType> {
    return this.http.put<IAreaTrainer>(`${this.resourceUrl}/${getAreaTrainerIdentifier(areaTrainer) as number}`, areaTrainer, {
      observe: 'response',
    });
  }

  partialUpdate(areaTrainer: IAreaTrainer): Observable<EntityResponseType> {
    return this.http.patch<IAreaTrainer>(`${this.resourceUrl}/${getAreaTrainerIdentifier(areaTrainer) as number}`, areaTrainer, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAreaTrainer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAreaTrainer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAreaTrainerToCollectionIfMissing(
    areaTrainerCollection: IAreaTrainer[],
    ...areaTrainersToCheck: (IAreaTrainer | null | undefined)[]
  ): IAreaTrainer[] {
    const areaTrainers: IAreaTrainer[] = areaTrainersToCheck.filter(isPresent);
    if (areaTrainers.length > 0) {
      const areaTrainerCollectionIdentifiers = areaTrainerCollection.map(areaTrainerItem => getAreaTrainerIdentifier(areaTrainerItem)!);
      const areaTrainersToAdd = areaTrainers.filter(areaTrainerItem => {
        const areaTrainerIdentifier = getAreaTrainerIdentifier(areaTrainerItem);
        if (areaTrainerIdentifier == null || areaTrainerCollectionIdentifiers.includes(areaTrainerIdentifier)) {
          return false;
        }
        areaTrainerCollectionIdentifiers.push(areaTrainerIdentifier);
        return true;
      });
      return [...areaTrainersToAdd, ...areaTrainerCollection];
    }
    return areaTrainerCollection;
  }
}
