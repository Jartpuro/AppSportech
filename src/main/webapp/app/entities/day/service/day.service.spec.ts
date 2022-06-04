import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { State } from 'app/entities/enumerations/state.model';
import { IDay, Day } from '../day.model';

import { DayService } from './day.service';

describe('Day Service', () => {
  let service: DayService;
  let httpMock: HttpTestingController;
  let elemDefault: IDay;
  let expectedResult: IDay | IDay[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DayService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      dayName: 'AAAAAAA',
      dayState: State.ACTIVE,
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

    it('should create a Day', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Day()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Day', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dayName: 'BBBBBB',
          dayState: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Day', () => {
      const patchObject = Object.assign(
        {
          dayName: 'BBBBBB',
          dayState: 'BBBBBB',
        },
        new Day()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Day', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          dayName: 'BBBBBB',
          dayState: 'BBBBBB',
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

    it('should delete a Day', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addDayToCollectionIfMissing', () => {
      it('should add a Day to an empty array', () => {
        const day: IDay = { id: 123 };
        expectedResult = service.addDayToCollectionIfMissing([], day);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(day);
      });

      it('should not add a Day to an array that contains it', () => {
        const day: IDay = { id: 123 };
        const dayCollection: IDay[] = [
          {
            ...day,
          },
          { id: 456 },
        ];
        expectedResult = service.addDayToCollectionIfMissing(dayCollection, day);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Day to an array that doesn't contain it", () => {
        const day: IDay = { id: 123 };
        const dayCollection: IDay[] = [{ id: 456 }];
        expectedResult = service.addDayToCollectionIfMissing(dayCollection, day);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(day);
      });

      it('should add only unique Day to an array', () => {
        const dayArray: IDay[] = [{ id: 123 }, { id: 456 }, { id: 36189 }];
        const dayCollection: IDay[] = [{ id: 123 }];
        expectedResult = service.addDayToCollectionIfMissing(dayCollection, ...dayArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const day: IDay = { id: 123 };
        const day2: IDay = { id: 456 };
        expectedResult = service.addDayToCollectionIfMissing([], day, day2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(day);
        expect(expectedResult).toContain(day2);
      });

      it('should accept null and undefined values', () => {
        const day: IDay = { id: 123 };
        expectedResult = service.addDayToCollectionIfMissing([], null, day, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(day);
      });

      it('should return initial array if no Day is added', () => {
        const dayCollection: IDay[] = [{ id: 123 }];
        expectedResult = service.addDayToCollectionIfMissing(dayCollection, undefined, null);
        expect(expectedResult).toEqual(dayCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
