import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBonding, getBondingIdentifier } from '../bonding.model';

export type EntityResponseType = HttpResponse<IBonding>;
export type EntityArrayResponseType = HttpResponse<IBonding[]>;

@Injectable({ providedIn: 'root' })
export class BondingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bondings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bonding: IBonding): Observable<EntityResponseType> {
    return this.http.post<IBonding>(this.resourceUrl, bonding, { observe: 'response' });
  }

  update(bonding: IBonding): Observable<EntityResponseType> {
    return this.http.put<IBonding>(`${this.resourceUrl}/${getBondingIdentifier(bonding) as number}`, bonding, { observe: 'response' });
  }

  partialUpdate(bonding: IBonding): Observable<EntityResponseType> {
    return this.http.patch<IBonding>(`${this.resourceUrl}/${getBondingIdentifier(bonding) as number}`, bonding, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBonding>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBonding[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBondingToCollectionIfMissing(bondingCollection: IBonding[], ...bondingsToCheck: (IBonding | null | undefined)[]): IBonding[] {
    const bondings: IBonding[] = bondingsToCheck.filter(isPresent);
    if (bondings.length > 0) {
      const bondingCollectionIdentifiers = bondingCollection.map(bondingItem => getBondingIdentifier(bondingItem)!);
      const bondingsToAdd = bondings.filter(bondingItem => {
        const bondingIdentifier = getBondingIdentifier(bondingItem);
        if (bondingIdentifier == null || bondingCollectionIdentifiers.includes(bondingIdentifier)) {
          return false;
        }
        bondingCollectionIdentifiers.push(bondingIdentifier);
        return true;
      });
      return [...bondingsToAdd, ...bondingCollection];
    }
    return bondingCollection;
  }
}
