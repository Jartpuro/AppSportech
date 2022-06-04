import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILogAudit, getLogAuditIdentifier } from '../log-audit.model';

export type EntityResponseType = HttpResponse<ILogAudit>;
export type EntityArrayResponseType = HttpResponse<ILogAudit[]>;

@Injectable({ providedIn: 'root' })
export class LogAuditService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/log-audits');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(logAudit: ILogAudit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(logAudit);
    return this.http
      .post<ILogAudit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(logAudit: ILogAudit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(logAudit);
    return this.http
      .put<ILogAudit>(`${this.resourceUrl}/${getLogAuditIdentifier(logAudit) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(logAudit: ILogAudit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(logAudit);
    return this.http
      .patch<ILogAudit>(`${this.resourceUrl}/${getLogAuditIdentifier(logAudit) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILogAudit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILogAudit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLogAuditToCollectionIfMissing(logAuditCollection: ILogAudit[], ...logAuditsToCheck: (ILogAudit | null | undefined)[]): ILogAudit[] {
    const logAudits: ILogAudit[] = logAuditsToCheck.filter(isPresent);
    if (logAudits.length > 0) {
      const logAuditCollectionIdentifiers = logAuditCollection.map(logAuditItem => getLogAuditIdentifier(logAuditItem)!);
      const logAuditsToAdd = logAudits.filter(logAuditItem => {
        const logAuditIdentifier = getLogAuditIdentifier(logAuditItem);
        if (logAuditIdentifier == null || logAuditCollectionIdentifiers.includes(logAuditIdentifier)) {
          return false;
        }
        logAuditCollectionIdentifiers.push(logAuditIdentifier);
        return true;
      });
      return [...logAuditsToAdd, ...logAuditCollection];
    }
    return logAuditCollection;
  }

  protected convertDateFromClient(logAudit: ILogAudit): ILogAudit {
    return Object.assign({}, logAudit, {
      dateAudit: logAudit.dateAudit?.isValid() ? logAudit.dateAudit.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAudit = res.body.dateAudit ? dayjs(res.body.dateAudit) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((logAudit: ILogAudit) => {
        logAudit.dateAudit = logAudit.dateAudit ? dayjs(logAudit.dateAudit) : undefined;
      });
    }
    return res;
  }
}
