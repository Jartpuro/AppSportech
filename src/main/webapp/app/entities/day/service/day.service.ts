import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDay, getDayIdentifier } from '../day.model';

export type EntityResponseType = HttpResponse<IDay>;
export type EntityArrayResponseType = HttpResponse<IDay[]>;

@Injectable({ providedIn: 'root' })
export class DayService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/days');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(day: IDay): Observable<EntityResponseType> {
    return this.http.post<IDay>(this.resourceUrl, day, { observe: 'response' });
  }

  update(day: IDay): Observable<EntityResponseType> {
    return this.http.put<IDay>(`${this.resourceUrl}/${getDayIdentifier(day) as number}`, day, { observe: 'response' });
  }

  partialUpdate(day: IDay): Observable<EntityResponseType> {
    return this.http.patch<IDay>(`${this.resourceUrl}/${getDayIdentifier(day) as number}`, day, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDay>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDay[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addDayToCollectionIfMissing(dayCollection: IDay[], ...daysToCheck: (IDay | null | undefined)[]): IDay[] {
    const days: IDay[] = daysToCheck.filter(isPresent);
    if (days.length > 0) {
      const dayCollectionIdentifiers = dayCollection.map(dayItem => getDayIdentifier(dayItem)!);
      const daysToAdd = days.filter(dayItem => {
        const dayIdentifier = getDayIdentifier(dayItem);
        if (dayIdentifier == null || dayCollectionIdentifiers.includes(dayIdentifier)) {
          return false;
        }
        dayCollectionIdentifiers.push(dayIdentifier);
        return true;
      });
      return [...daysToAdd, ...dayCollection];
    }
    return dayCollection;
  }
}
