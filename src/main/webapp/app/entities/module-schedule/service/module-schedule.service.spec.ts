import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IModuleSchedule, ModuleSchedule } from '../module-schedule.model';

import { ModuleScheduleService } from './module-schedule.service';

describe('ModuleSchedule Service', () => {
  let service: ModuleScheduleService;
  let httpMock: HttpTestingController;
  let elemDefault: IModuleSchedule;
  let expectedResult: IModuleSchedule | IModuleSchedule[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ModuleScheduleService);
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

    it('should create a ModuleSchedule', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new ModuleSchedule()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ModuleSchedule', () => {
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

    it('should partial update a ModuleSchedule', () => {
      const patchObject = Object.assign({}, new ModuleSchedule());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ModuleSchedule', () => {
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

    it('should delete a ModuleSchedule', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addModuleScheduleToCollectionIfMissing', () => {
      it('should add a ModuleSchedule to an empty array', () => {
        const moduleSchedule: IModuleSchedule = { id: 123 };
        expectedResult = service.addModuleScheduleToCollectionIfMissing([], moduleSchedule);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(moduleSchedule);
      });

      it('should not add a ModuleSchedule to an array that contains it', () => {
        const moduleSchedule: IModuleSchedule = { id: 123 };
        const moduleScheduleCollection: IModuleSchedule[] = [
          {
            ...moduleSchedule,
          },
          { id: 456 },
        ];
        expectedResult = service.addModuleScheduleToCollectionIfMissing(moduleScheduleCollection, moduleSchedule);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ModuleSchedule to an array that doesn't contain it", () => {
        const moduleSchedule: IModuleSchedule = { id: 123 };
        const moduleScheduleCollection: IModuleSchedule[] = [{ id: 456 }];
        expectedResult = service.addModuleScheduleToCollectionIfMissing(moduleScheduleCollection, moduleSchedule);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(moduleSchedule);
      });

      it('should add only unique ModuleSchedule to an array', () => {
        const moduleScheduleArray: IModuleSchedule[] = [{ id: 123 }, { id: 456 }, { id: 28041 }];
        const moduleScheduleCollection: IModuleSchedule[] = [{ id: 123 }];
        expectedResult = service.addModuleScheduleToCollectionIfMissing(moduleScheduleCollection, ...moduleScheduleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const moduleSchedule: IModuleSchedule = { id: 123 };
        const moduleSchedule2: IModuleSchedule = { id: 456 };
        expectedResult = service.addModuleScheduleToCollectionIfMissing([], moduleSchedule, moduleSchedule2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(moduleSchedule);
        expect(expectedResult).toContain(moduleSchedule2);
      });

      it('should accept null and undefined values', () => {
        const moduleSchedule: IModuleSchedule = { id: 123 };
        expectedResult = service.addModuleScheduleToCollectionIfMissing([], null, moduleSchedule, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(moduleSchedule);
      });

      it('should return initial array if no ModuleSchedule is added', () => {
        const moduleScheduleCollection: IModuleSchedule[] = [{ id: 123 }];
        expectedResult = service.addModuleScheduleToCollectionIfMissing(moduleScheduleCollection, undefined, null);
        expect(expectedResult).toEqual(moduleScheduleCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
