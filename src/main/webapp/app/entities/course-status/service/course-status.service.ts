import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICourseStatus, getCourseStatusIdentifier } from '../course-status.model';

export type EntityResponseType = HttpResponse<ICourseStatus>;
export type EntityArrayResponseType = HttpResponse<ICourseStatus[]>;

@Injectable({ providedIn: 'root' })
export class CourseStatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/course-statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(courseStatus: ICourseStatus): Observable<EntityResponseType> {
    return this.http.post<ICourseStatus>(this.resourceUrl, courseStatus, { observe: 'response' });
  }

  update(courseStatus: ICourseStatus): Observable<EntityResponseType> {
    return this.http.put<ICourseStatus>(`${this.resourceUrl}/${getCourseStatusIdentifier(courseStatus) as number}`, courseStatus, {
      observe: 'response',
    });
  }

  partialUpdate(courseStatus: ICourseStatus): Observable<EntityResponseType> {
    return this.http.patch<ICourseStatus>(`${this.resourceUrl}/${getCourseStatusIdentifier(courseStatus) as number}`, courseStatus, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICourseStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICourseStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCourseStatusToCollectionIfMissing(
    courseStatusCollection: ICourseStatus[],
    ...courseStatusesToCheck: (ICourseStatus | null | undefined)[]
  ): ICourseStatus[] {
    const courseStatuses: ICourseStatus[] = courseStatusesToCheck.filter(isPresent);
    if (courseStatuses.length > 0) {
      const courseStatusCollectionIdentifiers = courseStatusCollection.map(
        courseStatusItem => getCourseStatusIdentifier(courseStatusItem)!
      );
      const courseStatusesToAdd = courseStatuses.filter(courseStatusItem => {
        const courseStatusIdentifier = getCourseStatusIdentifier(courseStatusItem);
        if (courseStatusIdentifier == null || courseStatusCollectionIdentifiers.includes(courseStatusIdentifier)) {
          return false;
        }
        courseStatusCollectionIdentifiers.push(courseStatusIdentifier);
        return true;
      });
      return [...courseStatusesToAdd, ...courseStatusCollection];
    }
    return courseStatusCollection;
  }
}
