import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILogAudit, LogAudit } from '../log-audit.model';

import { LogAuditService } from './log-audit.service';

describe('LogAudit Service', () => {
  let service: LogAuditService;
  let httpMock: HttpTestingController;
  let elemDefault: ILogAudit;
  let expectedResult: ILogAudit | ILogAudit[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LogAuditService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      levelAudit: 'AAAAAAA',
      logName: 'AAAAAAA',
      messageAudit: 'AAAAAAA',
      dateAudit: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateAudit: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a LogAudit', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateAudit: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAudit: currentDate,
        },
        returnedFromService
      );

      service.create(new LogAudit()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LogAudit', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          levelAudit: 'BBBBBB',
          logName: 'BBBBBB',
          messageAudit: 'BBBBBB',
          dateAudit: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAudit: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LogAudit', () => {
      const patchObject = Object.assign(
        {
          dateAudit: currentDate.format(DATE_TIME_FORMAT),
        },
        new LogAudit()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateAudit: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LogAudit', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          levelAudit: 'BBBBBB',
          logName: 'BBBBBB',
          messageAudit: 'BBBBBB',
          dateAudit: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateAudit: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a LogAudit', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLogAuditToCollectionIfMissing', () => {
      it('should add a LogAudit to an empty array', () => {
        const logAudit: ILogAudit = { id: 123 };
        expectedResult = service.addLogAuditToCollectionIfMissing([], logAudit);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(logAudit);
      });

      it('should not add a LogAudit to an array that contains it', () => {
        const logAudit: ILogAudit = { id: 123 };
        const logAuditCollection: ILogAudit[] = [
          {
            ...logAudit,
          },
          { id: 456 },
        ];
        expectedResult = service.addLogAuditToCollectionIfMissing(logAuditCollection, logAudit);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LogAudit to an array that doesn't contain it", () => {
        const logAudit: ILogAudit = { id: 123 };
        const logAuditCollection: ILogAudit[] = [{ id: 456 }];
        expectedResult = service.addLogAuditToCollectionIfMissing(logAuditCollection, logAudit);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(logAudit);
      });

      it('should add only unique LogAudit to an array', () => {
        const logAuditArray: ILogAudit[] = [{ id: 123 }, { id: 456 }, { id: 63487 }];
        const logAuditCollection: ILogAudit[] = [{ id: 123 }];
        expectedResult = service.addLogAuditToCollectionIfMissing(logAuditCollection, ...logAuditArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const logAudit: ILogAudit = { id: 123 };
        const logAudit2: ILogAudit = { id: 456 };
        expectedResult = service.addLogAuditToCollectionIfMissing([], logAudit, logAudit2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(logAudit);
        expect(expectedResult).toContain(logAudit2);
      });

      it('should accept null and undefined values', () => {
        const logAudit: ILogAudit = { id: 123 };
        expectedResult = service.addLogAuditToCollectionIfMissing([], null, logAudit, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(logAudit);
      });

      it('should return initial array if no LogAudit is added', () => {
        const logAuditCollection: ILogAudit[] = [{ id: 123 }];
        expectedResult = service.addLogAuditToCollectionIfMissing(logAuditCollection, undefined, null);
        expect(expectedResult).toEqual(logAuditCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
