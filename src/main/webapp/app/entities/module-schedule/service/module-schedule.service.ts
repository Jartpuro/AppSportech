import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IModuleSchedule, getModuleScheduleIdentifier } from '../module-schedule.model';

export type EntityResponseType = HttpResponse<IModuleSchedule>;
export type EntityArrayResponseType = HttpResponse<IModuleSchedule[]>;

@Injectable({ providedIn: 'root' })
export class ModuleScheduleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/module-schedules');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(moduleSchedule: IModuleSchedule): Observable<EntityResponseType> {
    return this.http.post<IModuleSchedule>(this.resourceUrl, moduleSchedule, { observe: 'response' });
  }

  update(moduleSchedule: IModuleSchedule): Observable<EntityResponseType> {
    return this.http.put<IModuleSchedule>(`${this.resourceUrl}/${getModuleScheduleIdentifier(moduleSchedule) as number}`, moduleSchedule, {
      observe: 'response',
    });
  }

  partialUpdate(moduleSchedule: IModuleSchedule): Observable<EntityResponseType> {
    return this.http.patch<IModuleSchedule>(
      `${this.resourceUrl}/${getModuleScheduleIdentifier(moduleSchedule) as number}`,
      moduleSchedule,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IModuleSchedule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IModuleSchedule[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addModuleScheduleToCollectionIfMissing(
    moduleScheduleCollection: IModuleSchedule[],
    ...moduleSchedulesToCheck: (IModuleSchedule | null | undefined)[]
  ): IModuleSchedule[] {
    const moduleSchedules: IModuleSchedule[] = moduleSchedulesToCheck.filter(isPresent);
    if (moduleSchedules.length > 0) {
      const moduleScheduleCollectionIdentifiers = moduleScheduleCollection.map(
        moduleScheduleItem => getModuleScheduleIdentifier(moduleScheduleItem)!
      );
      const moduleSchedulesToAdd = moduleSchedules.filter(moduleScheduleItem => {
        const moduleScheduleIdentifier = getModuleScheduleIdentifier(moduleScheduleItem);
        if (moduleScheduleIdentifier == null || moduleScheduleCollectionIdentifiers.includes(moduleScheduleIdentifier)) {
          return false;
        }
        moduleScheduleCollectionIdentifiers.push(moduleScheduleIdentifier);
        return true;
      });
      return [...moduleSchedulesToAdd, ...moduleScheduleCollection];
    }
    return moduleScheduleCollection;
  }
}
