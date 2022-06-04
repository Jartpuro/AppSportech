import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IYear, getYearIdentifier } from '../year.model';

export type EntityResponseType = HttpResponse<IYear>;
export type EntityArrayResponseType = HttpResponse<IYear[]>;

@Injectable({ providedIn: 'root' })
export class YearService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/years');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(year: IYear): Observable<EntityResponseType> {
    return this.http.post<IYear>(this.resourceUrl, year, { observe: 'response' });
  }

  update(year: IYear): Observable<EntityResponseType> {
    return this.http.put<IYear>(`${this.resourceUrl}/${getYearIdentifier(year) as number}`, year, { observe: 'response' });
  }

  partialUpdate(year: IYear): Observable<EntityResponseType> {
    return this.http.patch<IYear>(`${this.resourceUrl}/${getYearIdentifier(year) as number}`, year, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IYear>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IYear[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addYearToCollectionIfMissing(yearCollection: IYear[], ...yearsToCheck: (IYear | null | undefined)[]): IYear[] {
    const years: IYear[] = yearsToCheck.filter(isPresent);
    if (years.length > 0) {
      const yearCollectionIdentifiers = yearCollection.map(yearItem => getYearIdentifier(yearItem)!);
      const yearsToAdd = years.filter(yearItem => {
        const yearIdentifier = getYearIdentifier(yearItem);
        if (yearIdentifier == null || yearCollectionIdentifiers.includes(yearIdentifier)) {
          return false;
        }
        yearCollectionIdentifiers.push(yearIdentifier);
        return true;
      });
      return [...yearsToAdd, ...yearCollection];
    }
    return yearCollection;
  }
}
