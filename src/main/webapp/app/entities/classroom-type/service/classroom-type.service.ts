import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IClassroomType, getClassroomTypeIdentifier } from '../classroom-type.model';

export type EntityResponseType = HttpResponse<IClassroomType>;
export type EntityArrayResponseType = HttpResponse<IClassroomType[]>;

@Injectable({ providedIn: 'root' })
export class ClassroomTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/classroom-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(classroomType: IClassroomType): Observable<EntityResponseType> {
    return this.http.post<IClassroomType>(this.resourceUrl, classroomType, { observe: 'response' });
  }

  update(classroomType: IClassroomType): Observable<EntityResponseType> {
    return this.http.put<IClassroomType>(`${this.resourceUrl}/${getClassroomTypeIdentifier(classroomType) as number}`, classroomType, {
      observe: 'response',
    });
  }

  partialUpdate(classroomType: IClassroomType): Observable<EntityResponseType> {
    return this.http.patch<IClassroomType>(`${this.resourceUrl}/${getClassroomTypeIdentifier(classroomType) as number}`, classroomType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClassroomType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClassroomType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addClassroomTypeToCollectionIfMissing(
    classroomTypeCollection: IClassroomType[],
    ...classroomTypesToCheck: (IClassroomType | null | undefined)[]
  ): IClassroomType[] {
    const classroomTypes: IClassroomType[] = classroomTypesToCheck.filter(isPresent);
    if (classroomTypes.length > 0) {
      const classroomTypeCollectionIdentifiers = classroomTypeCollection.map(
        classroomTypeItem => getClassroomTypeIdentifier(classroomTypeItem)!
      );
      const classroomTypesToAdd = classroomTypes.filter(classroomTypeItem => {
        const classroomTypeIdentifier = getClassroomTypeIdentifier(classroomTypeItem);
        if (classroomTypeIdentifier == null || classroomTypeCollectionIdentifiers.includes(classroomTypeIdentifier)) {
          return false;
        }
        classroomTypeCollectionIdentifiers.push(classroomTypeIdentifier);
        return true;
      });
      return [...classroomTypesToAdd, ...classroomTypeCollection];
    }
    return classroomTypeCollection;
  }
}
