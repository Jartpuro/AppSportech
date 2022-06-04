import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITrainingProgram, getTrainingProgramIdentifier } from '../training-program.model';

export type EntityResponseType = HttpResponse<ITrainingProgram>;
export type EntityArrayResponseType = HttpResponse<ITrainingProgram[]>;

@Injectable({ providedIn: 'root' })
export class TrainingProgramService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/training-programs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(trainingProgram: ITrainingProgram): Observable<EntityResponseType> {
    return this.http.post<ITrainingProgram>(this.resourceUrl, trainingProgram, { observe: 'response' });
  }

  update(trainingProgram: ITrainingProgram): Observable<EntityResponseType> {
    return this.http.put<ITrainingProgram>(
      `${this.resourceUrl}/${getTrainingProgramIdentifier(trainingProgram) as number}`,
      trainingProgram,
      { observe: 'response' }
    );
  }

  partialUpdate(trainingProgram: ITrainingProgram): Observable<EntityResponseType> {
    return this.http.patch<ITrainingProgram>(
      `${this.resourceUrl}/${getTrainingProgramIdentifier(trainingProgram) as number}`,
      trainingProgram,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrainingProgram>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainingProgram[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTrainingProgramToCollectionIfMissing(
    trainingProgramCollection: ITrainingProgram[],
    ...trainingProgramsToCheck: (ITrainingProgram | null | undefined)[]
  ): ITrainingProgram[] {
    const trainingPrograms: ITrainingProgram[] = trainingProgramsToCheck.filter(isPresent);
    if (trainingPrograms.length > 0) {
      const trainingProgramCollectionIdentifiers = trainingProgramCollection.map(
        trainingProgramItem => getTrainingProgramIdentifier(trainingProgramItem)!
      );
      const trainingProgramsToAdd = trainingPrograms.filter(trainingProgramItem => {
        const trainingProgramIdentifier = getTrainingProgramIdentifier(trainingProgramItem);
        if (trainingProgramIdentifier == null || trainingProgramCollectionIdentifiers.includes(trainingProgramIdentifier)) {
          return false;
        }
        trainingProgramCollectionIdentifiers.push(trainingProgramIdentifier);
        return true;
      });
      return [...trainingProgramsToAdd, ...trainingProgramCollection];
    }
    return trainingProgramCollection;
  }
}
