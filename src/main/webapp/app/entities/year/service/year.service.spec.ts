import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { State } from 'app/entities/enumerations/state.model';
import { IYear, Year } from '../year.model';

import { YearService } from './year.service';

describe('Year Service', () => {
  let service: YearService;
  let httpMock: HttpTestingController;
  let elemDefault: IYear;
  let expectedResult: IYear | IYear[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(YearService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      yearNumber: 0,
      yearState: State.ACTIVE,
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

    it('should create a Year', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Year()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Year', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          yearNumber: 1,
          yearState: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Year', () => {
      const patchObject = Object.assign(
        {
          yearNumber: 1,
          yearState: 'BBBBBB',
        },
        new Year()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Year', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          yearNumber: 1,
          yearState: 'BBBBBB',
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

    it('should delete a Year', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addYearToCollectionIfMissing', () => {
      it('should add a Year to an empty array', () => {
        const year: IYear = { id: 123 };
        expectedResult = service.addYearToCollectionIfMissing([], year);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(year);
      });

      it('should not add a Year to an array that contains it', () => {
        const year: IYear = { id: 123 };
        const yearCollection: IYear[] = [
          {
            ...year,
          },
          { id: 456 },
        ];
        expectedResult = service.addYearToCollectionIfMissing(yearCollection, year);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Year to an array that doesn't contain it", () => {
        const year: IYear = { id: 123 };
        const yearCollection: IYear[] = [{ id: 456 }];
        expectedResult = service.addYearToCollectionIfMissing(yearCollection, year);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(year);
      });

      it('should add only unique Year to an array', () => {
        const yearArray: IYear[] = [{ id: 123 }, { id: 456 }, { id: 69275 }];
        const yearCollection: IYear[] = [{ id: 123 }];
        expectedResult = service.addYearToCollectionIfMissing(yearCollection, ...yearArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const year: IYear = { id: 123 };
        const year2: IYear = { id: 456 };
        expectedResult = service.addYearToCollectionIfMissing([], year, year2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(year);
        expect(expectedResult).toContain(year2);
      });

      it('should accept null and undefined values', () => {
        const year: IYear = { id: 123 };
        expectedResult = service.addYearToCollectionIfMissing([], null, year, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(year);
      });

      it('should return initial array if no Year is added', () => {
        const yearCollection: IYear[] = [{ id: 123 }];
        expectedResult = service.addYearToCollectionIfMissing(yearCollection, undefined, null);
        expect(expectedResult).toEqual(yearCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
