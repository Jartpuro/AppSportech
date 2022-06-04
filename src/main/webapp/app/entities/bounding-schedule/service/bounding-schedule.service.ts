import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBoundingSchedule, getBoundingScheduleIdentifier } from '../bounding-schedule.model';

export type EntityResponseType = HttpResponse<IBoundingSchedule>;
export type EntityArrayResponseType = HttpResponse<IBoundingSchedule[]>;

@Injectable({ providedIn: 'root' })
export class BoundingScheduleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bounding-schedules');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(boundingSchedule: IBoundingSchedule): Observable<EntityResponseType> {
    return this.http.post<IBoundingSchedule>(this.resourceUrl, boundingSchedule, { observe: 'response' });
  }

  update(boundingSchedule: IBoundingSchedule): Observable<EntityResponseType> {
    return this.http.put<IBoundingSchedule>(
      `${this.resourceUrl}/${getBoundingScheduleIdentifier(boundingSchedule) as number}`,
      boundingSchedule,
      { observe: 'response' }
    );
  }

  partialUpdate(boundingSchedule: IBoundingSchedule): Observable<EntityResponseType> {
    return this.http.patch<IBoundingSchedule>(
      `${this.resourceUrl}/${getBoundingScheduleIdentifier(boundingSchedule) as number}`,
      boundingSchedule,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBoundingSchedule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBoundingSchedule[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBoundingScheduleToCollectionIfMissing(
    boundingScheduleCollection: IBoundingSchedule[],
    ...boundingSchedulesToCheck: (IBoundingSchedule | null | undefined)[]
  ): IBoundingSchedule[] {
    const boundingSchedules: IBoundingSchedule[] = boundingSchedulesToCheck.filter(isPresent);
    if (boundingSchedules.length > 0) {
      const boundingScheduleCollectionIdentifiers = boundingScheduleCollection.map(
        boundingScheduleItem => getBoundingScheduleIdentifier(boundingScheduleItem)!
      );
      const boundingSchedulesToAdd = boundingSchedules.filter(boundingScheduleItem => {
        const boundingScheduleIdentifier = getBoundingScheduleIdentifier(boundingScheduleItem);
        if (boundingScheduleIdentifier == null || boundingScheduleCollectionIdentifiers.includes(boundingScheduleIdentifier)) {
          return false;
        }
        boundingScheduleCollectionIdentifiers.push(boundingScheduleIdentifier);
        return true;
      });
      return [...boundingSchedulesToAdd, ...boundingScheduleCollection];
    }
    return boundingScheduleCollection;
  }
}
