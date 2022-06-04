import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IModality, getModalityIdentifier } from '../modality.model';

export type EntityResponseType = HttpResponse<IModality>;
export type EntityArrayResponseType = HttpResponse<IModality[]>;

@Injectable({ providedIn: 'root' })
export class ModalityService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/modalities');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(modality: IModality): Observable<EntityResponseType> {
    return this.http.post<IModality>(this.resourceUrl, modality, { observe: 'response' });
  }

  update(modality: IModality): Observable<EntityResponseType> {
    return this.http.put<IModality>(`${this.resourceUrl}/${getModalityIdentifier(modality) as number}`, modality, { observe: 'response' });
  }

  partialUpdate(modality: IModality): Observable<EntityResponseType> {
    return this.http.patch<IModality>(`${this.resourceUrl}/${getModalityIdentifier(modality) as number}`, modality, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IModality>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IModality[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addModalityToCollectionIfMissing(modalityCollection: IModality[], ...modalitiesToCheck: (IModality | null | undefined)[]): IModality[] {
    const modalities: IModality[] = modalitiesToCheck.filter(isPresent);
    if (modalities.length > 0) {
      const modalityCollectionIdentifiers = modalityCollection.map(modalityItem => getModalityIdentifier(modalityItem)!);
      const modalitiesToAdd = modalities.filter(modalityItem => {
        const modalityIdentifier = getModalityIdentifier(modalityItem);
        if (modalityIdentifier == null || modalityCollectionIdentifiers.includes(modalityIdentifier)) {
          return false;
        }
        modalityCollectionIdentifiers.push(modalityIdentifier);
        return true;
      });
      return [...modalitiesToAdd, ...modalityCollection];
    }
    return modalityCollection;
  }
}
