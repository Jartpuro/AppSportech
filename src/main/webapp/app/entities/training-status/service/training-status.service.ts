import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITrainingStatus, getTrainingStatusIdentifier } from '../training-status.model';

export type EntityResponseType = HttpResponse<ITrainingStatus>;
export type EntityArrayResponseType = HttpResponse<ITrainingStatus[]>;

@Injectable({ providedIn: 'root' })
export class TrainingStatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/training-statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(trainingStatus: ITrainingStatus): Observable<EntityResponseType> {
    return this.http.post<ITrainingStatus>(this.resourceUrl, trainingStatus, { observe: 'response' });
  }

  update(trainingStatus: ITrainingStatus): Observable<EntityResponseType> {
    return this.http.put<ITrainingStatus>(`${this.resourceUrl}/${getTrainingStatusIdentifier(trainingStatus) as number}`, trainingStatus, {
      observe: 'response',
    });
  }

  partialUpdate(trainingStatus: ITrainingStatus): Observable<EntityResponseType> {
    return this.http.patch<ITrainingStatus>(
      `${this.resourceUrl}/${getTrainingStatusIdentifier(trainingStatus) as number}`,
      trainingStatus,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrainingStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainingStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTrainingStatusToCollectionIfMissing(
    trainingStatusCollection: ITrainingStatus[],
    ...trainingStatusesToCheck: (ITrainingStatus | null | undefined)[]
  ): ITrainingStatus[] {
    const trainingStatuses: ITrainingStatus[] = trainingStatusesToCheck.filter(isPresent);
    if (trainingStatuses.length > 0) {
      const trainingStatusCollectionIdentifiers = trainingStatusCollection.map(
        trainingStatusItem => getTrainingStatusIdentifier(trainingStatusItem)!
      );
      const trainingStatusesToAdd = trainingStatuses.filter(trainingStatusItem => {
        const trainingStatusIdentifier = getTrainingStatusIdentifier(trainingStatusItem);
        if (trainingStatusIdentifier == null || trainingStatusCollectionIdentifiers.includes(trainingStatusIdentifier)) {
          return false;
        }
        trainingStatusCollectionIdentifiers.push(trainingStatusIdentifier);
        return true;
      });
      return [...trainingStatusesToAdd, ...trainingStatusCollection];
    }
    return trainingStatusCollection;
  }
}
