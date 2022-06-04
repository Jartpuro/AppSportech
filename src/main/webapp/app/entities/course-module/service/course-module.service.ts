import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICourseModule, getCourseModuleIdentifier } from '../course-module.model';

export type EntityResponseType = HttpResponse<ICourseModule>;
export type EntityArrayResponseType = HttpResponse<ICourseModule[]>;

@Injectable({ providedIn: 'root' })
export class CourseModuleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/course-modules');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(courseModule: ICourseModule): Observable<EntityResponseType> {
    return this.http.post<ICourseModule>(this.resourceUrl, courseModule, { observe: 'response' });
  }

  update(courseModule: ICourseModule): Observable<EntityResponseType> {
    return this.http.put<ICourseModule>(`${this.resourceUrl}/${getCourseModuleIdentifier(courseModule) as number}`, courseModule, {
      observe: 'response',
    });
  }

  partialUpdate(courseModule: ICourseModule): Observable<EntityResponseType> {
    return this.http.patch<ICourseModule>(`${this.resourceUrl}/${getCourseModuleIdentifier(courseModule) as number}`, courseModule, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICourseModule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICourseModule[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCourseModuleToCollectionIfMissing(
    courseModuleCollection: ICourseModule[],
    ...courseModulesToCheck: (ICourseModule | null | undefined)[]
  ): ICourseModule[] {
    const courseModules: ICourseModule[] = courseModulesToCheck.filter(isPresent);
    if (courseModules.length > 0) {
      const courseModuleCollectionIdentifiers = courseModuleCollection.map(
        courseModuleItem => getCourseModuleIdentifier(courseModuleItem)!
      );
      const courseModulesToAdd = courseModules.filter(courseModuleItem => {
        const courseModuleIdentifier = getCourseModuleIdentifier(courseModuleItem);
        if (courseModuleIdentifier == null || courseModuleCollectionIdentifiers.includes(courseModuleIdentifier)) {
          return false;
        }
        courseModuleCollectionIdentifiers.push(courseModuleIdentifier);
        return true;
      });
      return [...courseModulesToAdd, ...courseModuleCollection];
    }
    return courseModuleCollection;
  }
}
