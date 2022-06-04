import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITrainee, getTraineeIdentifier } from '../trainee.model';

export type EntityResponseType = HttpResponse<ITrainee>;
export type EntityArrayResponseType = HttpResponse<ITrainee[]>;

@Injectable({ providedIn: 'root' })
export class TraineeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/trainees');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(trainee: ITrainee): Observable<EntityResponseType> {
    return this.http.post<ITrainee>(this.resourceUrl, trainee, { observe: 'response' });
  }

  update(trainee: ITrainee): Observable<EntityResponseType> {
    return this.http.put<ITrainee>(`${this.resourceUrl}/${getTraineeIdentifier(trainee) as number}`, trainee, { observe: 'response' });
  }

  partialUpdate(trainee: ITrainee): Observable<EntityResponseType> {
    return this.http.patch<ITrainee>(`${this.resourceUrl}/${getTraineeIdentifier(trainee) as number}`, trainee, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrainee>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainee[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTraineeToCollectionIfMissing(traineeCollection: ITrainee[], ...traineesToCheck: (ITrainee | null | undefined)[]): ITrainee[] {
    const trainees: ITrainee[] = traineesToCheck.filter(isPresent);
    if (trainees.length > 0) {
      const traineeCollectionIdentifiers = traineeCollection.map(traineeItem => getTraineeIdentifier(traineeItem)!);
      const traineesToAdd = trainees.filter(traineeItem => {
        const traineeIdentifier = getTraineeIdentifier(traineeItem);
        if (traineeIdentifier == null || traineeCollectionIdentifiers.includes(traineeIdentifier)) {
          return false;
        }
        traineeCollectionIdentifiers.push(traineeIdentifier);
        return true;
      });
      return [...traineesToAdd, ...traineeCollection];
    }
    return traineeCollection;
  }
}
