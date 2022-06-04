import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IScheduleVersion, getScheduleVersionIdentifier } from '../schedule-version.model';

export type EntityResponseType = HttpResponse<IScheduleVersion>;
export type EntityArrayResponseType = HttpResponse<IScheduleVersion[]>;

@Injectable({ providedIn: 'root' })
export class ScheduleVersionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/schedule-versions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(scheduleVersion: IScheduleVersion): Observable<EntityResponseType> {
    return this.http.post<IScheduleVersion>(this.resourceUrl, scheduleVersion, { observe: 'response' });
  }

  update(scheduleVersion: IScheduleVersion): Observable<EntityResponseType> {
    return this.http.put<IScheduleVersion>(
      `${this.resourceUrl}/${getScheduleVersionIdentifier(scheduleVersion) as number}`,
      scheduleVersion,
      { observe: 'response' }
    );
  }

  partialUpdate(scheduleVersion: IScheduleVersion): Observable<EntityResponseType> {
    return this.http.patch<IScheduleVersion>(
      `${this.resourceUrl}/${getScheduleVersionIdentifier(scheduleVersion) as number}`,
      scheduleVersion,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IScheduleVersion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IScheduleVersion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addScheduleVersionToCollectionIfMissing(
    scheduleVersionCollection: IScheduleVersion[],
    ...scheduleVersionsToCheck: (IScheduleVersion | null | undefined)[]
  ): IScheduleVersion[] {
    const scheduleVersions: IScheduleVersion[] = scheduleVersionsToCheck.filter(isPresent);
    if (scheduleVersions.length > 0) {
      const scheduleVersionCollectionIdentifiers = scheduleVersionCollection.map(
        scheduleVersionItem => getScheduleVersionIdentifier(scheduleVersionItem)!
      );
      const scheduleVersionsToAdd = scheduleVersions.filter(scheduleVersionItem => {
        const scheduleVersionIdentifier = getScheduleVersionIdentifier(scheduleVersionItem);
        if (scheduleVersionIdentifier == null || scheduleVersionCollectionIdentifiers.includes(scheduleVersionIdentifier)) {
          return false;
        }
        scheduleVersionCollectionIdentifiers.push(scheduleVersionIdentifier);
        return true;
      });
      return [...scheduleVersionsToAdd, ...scheduleVersionCollection];
    }
    return scheduleVersionCollection;
  }
}
