import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { State } from 'app/entities/enumerations/state.model';
import { IBonding, Bonding } from '../bonding.model';

import { BondingService } from './bonding.service';

describe('Bonding Service', () => {
  let service: BondingService;
  let httpMock: HttpTestingController;
  let elemDefault: IBonding;
  let expectedResult: IBonding | IBonding[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BondingService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      bondingType: 'AAAAAAA',
      workingHours: 0,
      bondingState: State.ACTIVE,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Bonding', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Bonding()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Bonding', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          bondingType: 'BBBBBB',
          workingHours: 1,
          bondingState: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Bonding', () => {
      const patchObject = Object.assign(
        {
          workingHours: 1,
        },
        new Bonding()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Bonding', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          bondingType: 'BBBBBB',
          workingHours: 1,
          bondingState: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Bonding', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBondingToCollectionIfMissing', () => {
      it('should add a Bonding to an empty array', () => {
        const bonding: IBonding = { id: 123 };
        expectedResult = service.addBondingToCollectionIfMissing([], bonding);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bonding);
      });

      it('should not add a Bonding to an array that contains it', () => {
        const bonding: IBonding = { id: 123 };
        const bondingCollection: IBonding[] = [
          {
            ...bonding,
          },
          { id: 456 },
        ];
        expectedResult = service.addBondingToCollectionIfMissing(bondingCollection, bonding);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Bonding to an array that doesn't contain it", () => {
        const bonding: IBonding = { id: 123 };
        const bondingCollection: IBonding[] = [{ id: 456 }];
        expectedResult = service.addBondingToCollectionIfMissing(bondingCollection, bonding);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bonding);
      });

      it('should add only unique Bonding to an array', () => {
        const bondingArray: IBonding[] = [{ id: 123 }, { id: 456 }, { id: 79896 }];
        const bondingCollection: IBonding[] = [{ id: 123 }];
        expectedResult = service.addBondingToCollectionIfMissing(bondingCollection, ...bondingArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bonding: IBonding = { id: 123 };
        const bonding2: IBonding = { id: 456 };
        expectedResult = service.addBondingToCollectionIfMissing([], bonding, bonding2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bonding);
        expect(expectedResult).toContain(bonding2);
      });

      it('should accept null and undefined values', () => {
        const bonding: IBonding = { id: 123 };
        expectedResult = service.addBondingToCollectionIfMissing([], null, bonding, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bonding);
      });

      it('should return initial array if no Bonding is added', () => {
        const bondingCollection: IBonding[] = [{ id: 123 }];
        expectedResult = service.addBondingToCollectionIfMissing(bondingCollection, undefined, null);
        expect(expectedResult).toEqual(bondingCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
