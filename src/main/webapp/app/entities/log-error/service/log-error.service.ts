import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILogError, getLogErrorIdentifier } from '../log-error.model';

export type EntityResponseType = HttpResponse<ILogError>;
export type EntityArrayResponseType = HttpResponse<ILogError[]>;

@Injectable({ providedIn: 'root' })
export class LogErrorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/log-errors');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(logError: ILogError): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(logError);
    return this.http
      .post<ILogError>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(logError: ILogError): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(logError);
    return this.http
      .put<ILogError>(`${this.resourceUrl}/${getLogErrorIdentifier(logError) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(logError: ILogError): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(logError);
    return this.http
      .patch<ILogError>(`${this.resourceUrl}/${getLogErrorIdentifier(logError) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILogError>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILogError[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLogErrorToCollectionIfMissing(logErrorCollection: ILogError[], ...logErrorsToCheck: (ILogError | null | undefined)[]): ILogError[] {
    const logErrors: ILogError[] = logErrorsToCheck.filter(isPresent);
    if (logErrors.length > 0) {
      const logErrorCollectionIdentifiers = logErrorCollection.map(logErrorItem => getLogErrorIdentifier(logErrorItem)!);
      const logErrorsToAdd = logErrors.filter(logErrorItem => {
        const logErrorIdentifier = getLogErrorIdentifier(logErrorItem);
        if (logErrorIdentifier == null || logErrorCollectionIdentifiers.includes(logErrorIdentifier)) {
          return false;
        }
        logErrorCollectionIdentifiers.push(logErrorIdentifier);
        return true;
      });
      return [...logErrorsToAdd, ...logErrorCollection];
    }
    return logErrorCollection;
  }

  protected convertDateFromClient(logError: ILogError): ILogError {
    return Object.assign({}, logError, {
      dateError: logError.dateError?.isValid() ? logError.dateError.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateError = res.body.dateError ? dayjs(res.body.dateError) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((logError: ILogError) => {
        logError.dateError = logError.dateError ? dayjs(logError.dateError) : undefined;
      });
    }
    return res;
  }
}
