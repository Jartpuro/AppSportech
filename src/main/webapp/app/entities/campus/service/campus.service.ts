import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICampus, getCampusIdentifier } from '../campus.model';

export type EntityResponseType = HttpResponse<ICampus>;
export type EntityArrayResponseType = HttpResponse<ICampus[]>;

@Injectable({ providedIn: 'root' })
export class CampusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/campuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(campus: ICampus): Observable<EntityResponseType> {
    return this.http.post<ICampus>(this.resourceUrl, campus, { observe: 'response' });
  }

  update(campus: ICampus): Observable<EntityResponseType> {
    return this.http.put<ICampus>(`${this.resourceUrl}/${getCampusIdentifier(campus) as number}`, campus, { observe: 'response' });
  }

  partialUpdate(campus: ICampus): Observable<EntityResponseType> {
    return this.http.patch<ICampus>(`${this.resourceUrl}/${getCampusIdentifier(campus) as number}`, campus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICampus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICampus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCampusToCollectionIfMissing(campusCollection: ICampus[], ...campusesToCheck: (ICampus | null | undefined)[]): ICampus[] {
    const campuses: ICampus[] = campusesToCheck.filter(isPresent);
    if (campuses.length > 0) {
      const campusCollectionIdentifiers = campusCollection.map(campusItem => getCampusIdentifier(campusItem)!);
      const campusesToAdd = campuses.filter(campusItem => {
        const campusIdentifier = getCampusIdentifier(campusItem);
        if (campusIdentifier == null || campusCollectionIdentifiers.includes(campusIdentifier)) {
          return false;
        }
        campusCollectionIdentifiers.push(campusIdentifier);
        return true;
      });
      return [...campusesToAdd, ...campusCollection];
    }
    return campusCollection;
  }
}
