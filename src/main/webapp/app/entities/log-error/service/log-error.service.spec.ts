import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILogError, LogError } from '../log-error.model';

import { LogErrorService } from './log-error.service';

describe('LogError Service', () => {
  let service: LogErrorService;
  let httpMock: HttpTestingController;
  let elemDefault: ILogError;
  let expectedResult: ILogError | ILogError[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LogErrorService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      levelError: 'AAAAAAA',
      logName: 'AAAAAAA',
      messageError: 'AAAAAAA',
      dateError: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateError: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a LogError', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateError: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateError: currentDate,
        },
        returnedFromService
      );

      service.create(new LogError()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LogError', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          levelError: 'BBBBBB',
          logName: 'BBBBBB',
          messageError: 'BBBBBB',
          dateError: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateError: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LogError', () => {
      const patchObject = Object.assign(
        {
          messageError: 'BBBBBB',
          dateError: currentDate.format(DATE_TIME_FORMAT),
        },
        new LogError()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateError: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LogError', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          levelError: 'BBBBBB',
          logName: 'BBBBBB',
          messageError: 'BBBBBB',
          dateError: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateError: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a LogError', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLogErrorToCollectionIfMissing', () => {
      it('should add a LogError to an empty array', () => {
        const logError: ILogError = { id: 123 };
        expectedResult = service.addLogErrorToCollectionIfMissing([], logError);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(logError);
      });

      it('should not add a LogError to an array that contains it', () => {
        const logError: ILogError = { id: 123 };
        const logErrorCollection: ILogError[] = [
          {
            ...logError,
          },
          { id: 456 },
        ];
        expectedResult = service.addLogErrorToCollectionIfMissing(logErrorCollection, logError);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LogError to an array that doesn't contain it", () => {
        const logError: ILogError = { id: 123 };
        const logErrorCollection: ILogError[] = [{ id: 456 }];
        expectedResult = service.addLogErrorToCollectionIfMissing(logErrorCollection, logError);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(logError);
      });

      it('should add only unique LogError to an array', () => {
        const logErrorArray: ILogError[] = [{ id: 123 }, { id: 456 }, { id: 11359 }];
        const logErrorCollection: ILogError[] = [{ id: 123 }];
        expectedResult = service.addLogErrorToCollectionIfMissing(logErrorCollection, ...logErrorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const logError: ILogError = { id: 123 };
        const logError2: ILogError = { id: 456 };
        expectedResult = service.addLogErrorToCollectionIfMissing([], logError, logError2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(logError);
        expect(expectedResult).toContain(logError2);
      });

      it('should accept null and undefined values', () => {
        const logError: ILogError = { id: 123 };
        expectedResult = service.addLogErrorToCollectionIfMissing([], null, logError, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(logError);
      });

      it('should return initial array if no LogError is added', () => {
        const logErrorCollection: ILogError[] = [{ id: 123 }];
        expectedResult = service.addLogErrorToCollectionIfMissing(logErrorCollection, undefined, null);
        expect(expectedResult).toEqual(logErrorCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
