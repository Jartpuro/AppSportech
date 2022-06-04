import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITrainer, getTrainerIdentifier } from '../trainer.model';

export type EntityResponseType = HttpResponse<ITrainer>;
export type EntityArrayResponseType = HttpResponse<ITrainer[]>;

@Injectable({ providedIn: 'root' })
export class TrainerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/trainers');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(trainer: ITrainer): Observable<EntityResponseType> {
    return this.http.post<ITrainer>(this.resourceUrl, trainer, { observe: 'response' });
  }

  update(trainer: ITrainer): Observable<EntityResponseType> {
    return this.http.put<ITrainer>(`${this.resourceUrl}/${getTrainerIdentifier(trainer) as number}`, trainer, { observe: 'response' });
  }

  partialUpdate(trainer: ITrainer): Observable<EntityResponseType> {
    return this.http.patch<ITrainer>(`${this.resourceUrl}/${getTrainerIdentifier(trainer) as number}`, trainer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrainer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTrainerToCollectionIfMissing(trainerCollection: ITrainer[], ...trainersToCheck: (ITrainer | null | undefined)[]): ITrainer[] {
    const trainers: ITrainer[] = trainersToCheck.filter(isPresent);
    if (trainers.length > 0) {
      const trainerCollectionIdentifiers = trainerCollection.map(trainerItem => getTrainerIdentifier(trainerItem)!);
      const trainersToAdd = trainers.filter(trainerItem => {
        const trainerIdentifier = getTrainerIdentifier(trainerItem);
        if (trainerIdentifier == null || trainerCollectionIdentifiers.includes(trainerIdentifier)) {
          return false;
        }
        trainerCollectionIdentifiers.push(trainerIdentifier);
        return true;
      });
      return [...trainersToAdd, ...trainerCollection];
    }
    return trainerCollection;
  }
}
