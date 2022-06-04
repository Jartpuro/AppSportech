import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { State } from 'app/entities/enumerations/state.model';
import { IScheduleVersion, ScheduleVersion } from '../schedule-version.model';

import { ScheduleVersionService } from './schedule-version.service';

describe('ScheduleVersion Service', () => {
  let service: ScheduleVersionService;
  let httpMock: HttpTestingController;
  let elemDefault: IScheduleVersion;
  let expectedResult: IScheduleVersion | IScheduleVersion[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ScheduleVersionService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      versionNumber: 'AAAAAAA',
      versionState: State.ACTIVE,
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

    it('should create a ScheduleVersion', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ScheduleVersion()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ScheduleVersion', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          versionNumber: 'BBBBBB',
          versionState: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ScheduleVersion', () => {
      const patchObject = Object.assign(
        {
          versionNumber: 'BBBBBB',
          versionState: 'BBBBBB',
        },
        new ScheduleVersion()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ScheduleVersion', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          versionNumber: 'BBBBBB',
          versionState: 'BBBBBB',
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

    it('should delete a ScheduleVersion', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addScheduleVersionToCollectionIfMissing', () => {
      it('should add a ScheduleVersion to an empty array', () => {
        const scheduleVersion: IScheduleVersion = { id: 123 };
        expectedResult = service.addScheduleVersionToCollectionIfMissing([], scheduleVersion);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(scheduleVersion);
      });

      it('should not add a ScheduleVersion to an array that contains it', () => {
        const scheduleVersion: IScheduleVersion = { id: 123 };
        const scheduleVersionCollection: IScheduleVersion[] = [
          {
            ...scheduleVersion,
          },
          { id: 456 },
        ];
        expectedResult = service.addScheduleVersionToCollectionIfMissing(scheduleVersionCollection, scheduleVersion);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ScheduleVersion to an array that doesn't contain it", () => {
        const scheduleVersion: IScheduleVersion = { id: 123 };
        const scheduleVersionCollection: IScheduleVersion[] = [{ id: 456 }];
        expectedResult = service.addScheduleVersionToCollectionIfMissing(scheduleVersionCollection, scheduleVersion);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(scheduleVersion);
      });

      it('should add only unique ScheduleVersion to an array', () => {
        const scheduleVersionArray: IScheduleVersion[] = [{ id: 123 }, { id: 456 }, { id: 92670 }];
        const scheduleVersionCollection: IScheduleVersion[] = [{ id: 123 }];
        expectedResult = service.addScheduleVersionToCollectionIfMissing(scheduleVersionCollection, ...scheduleVersionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const scheduleVersion: IScheduleVersion = { id: 123 };
        const scheduleVersion2: IScheduleVersion = { id: 456 };
        expectedResult = service.addScheduleVersionToCollectionIfMissing([], scheduleVersion, scheduleVersion2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(scheduleVersion);
        expect(expectedResult).toContain(scheduleVersion2);
      });

      it('should accept null and undefined values', () => {
        const scheduleVersion: IScheduleVersion = { id: 123 };
        expectedResult = service.addScheduleVersionToCollectionIfMissing([], null, scheduleVersion, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(scheduleVersion);
      });

      it('should return initial array if no ScheduleVersion is added', () => {
        const scheduleVersionCollection: IScheduleVersion[] = [{ id: 123 }];
        expectedResult = service.addScheduleVersionToCollectionIfMissing(scheduleVersionCollection, undefined, null);
        expect(expectedResult).toEqual(scheduleVersionCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
