import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBoundingSchedule, BoundingSchedule } from '../bounding-schedule.model';

import { BoundingScheduleService } from './bounding-schedule.service';

describe('BoundingSchedule Service', () => {
  let service: BoundingScheduleService;
  let httpMock: HttpTestingController;
  let elemDefault: IBoundingSchedule;
  let expectedResult: IBoundingSchedule | IBoundingSchedule[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BoundingScheduleService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
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

    it('should create a BoundingSchedule', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new BoundingSchedule()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BoundingSchedule', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BoundingSchedule', () => {
      const patchObject = Object.assign({}, new BoundingSchedule());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BoundingSchedule', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
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

    it('should delete a BoundingSchedule', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBoundingScheduleToCollectionIfMissing', () => {
      it('should add a BoundingSchedule to an empty array', () => {
        const boundingSchedule: IBoundingSchedule = { id: 123 };
        expectedResult = service.addBoundingScheduleToCollectionIfMissing([], boundingSchedule);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(boundingSchedule);
      });

      it('should not add a BoundingSchedule to an array that contains it', () => {
        const boundingSchedule: IBoundingSchedule = { id: 123 };
        const boundingScheduleCollection: IBoundingSchedule[] = [
          {
            ...boundingSchedule,
          },
          { id: 456 },
        ];
        expectedResult = service.addBoundingScheduleToCollectionIfMissing(boundingScheduleCollection, boundingSchedule);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BoundingSchedule to an array that doesn't contain it", () => {
        const boundingSchedule: IBoundingSchedule = { id: 123 };
        const boundingScheduleCollection: IBoundingSchedule[] = [{ id: 456 }];
        expectedResult = service.addBoundingScheduleToCollectionIfMissing(boundingScheduleCollection, boundingSchedule);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(boundingSchedule);
      });

      it('should add only unique BoundingSchedule to an array', () => {
        const boundingScheduleArray: IBoundingSchedule[] = [{ id: 123 }, { id: 456 }, { id: 83284 }];
        const boundingScheduleCollection: IBoundingSchedule[] = [{ id: 123 }];
        expectedResult = service.addBoundingScheduleToCollectionIfMissing(boundingScheduleCollection, ...boundingScheduleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const boundingSchedule: IBoundingSchedule = { id: 123 };
        const boundingSchedule2: IBoundingSchedule = { id: 456 };
        expectedResult = service.addBoundingScheduleToCollectionIfMissing([], boundingSchedule, boundingSchedule2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(boundingSchedule);
        expect(expectedResult).toContain(boundingSchedule2);
      });

      it('should accept null and undefined values', () => {
        const boundingSchedule: IBoundingSchedule = { id: 123 };
        expectedResult = service.addBoundingScheduleToCollectionIfMissing([], null, boundingSchedule, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(boundingSchedule);
      });

      it('should return initial array if no BoundingSchedule is added', () => {
        const boundingScheduleCollection: IBoundingSchedule[] = [{ id: 123 }];
        expectedResult = service.addBoundingScheduleToCollectionIfMissing(boundingScheduleCollection, undefined, null);
        expect(expectedResult).toEqual(boundingScheduleCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
