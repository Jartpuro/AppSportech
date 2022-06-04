import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IBondingTrainer, BondingTrainer } from '../bonding-trainer.model';

import { BondingTrainerService } from './bonding-trainer.service';

describe('BondingTrainer Service', () => {
  let service: BondingTrainerService;
  let httpMock: HttpTestingController;
  let elemDefault: IBondingTrainer;
  let expectedResult: IBondingTrainer | IBondingTrainer[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BondingTrainerService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      startTime: currentDate,
      endTime: currentDate,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          startTime: currentDate.format(DATE_FORMAT),
          endTime: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a BondingTrainer', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          startTime: currentDate.format(DATE_FORMAT),
          endTime: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startTime: currentDate,
          endTime: currentDate,
        },
        returnedFromService
      );

      service.create(new BondingTrainer()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BondingTrainer', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          startTime: currentDate.format(DATE_FORMAT),
          endTime: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startTime: currentDate,
          endTime: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BondingTrainer', () => {
      const patchObject = Object.assign({}, new BondingTrainer());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          startTime: currentDate,
          endTime: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BondingTrainer', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          startTime: currentDate.format(DATE_FORMAT),
          endTime: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          startTime: currentDate,
          endTime: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a BondingTrainer', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBondingTrainerToCollectionIfMissing', () => {
      it('should add a BondingTrainer to an empty array', () => {
        const bondingTrainer: IBondingTrainer = { id: 123 };
        expectedResult = service.addBondingTrainerToCollectionIfMissing([], bondingTrainer);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bondingTrainer);
      });

      it('should not add a BondingTrainer to an array that contains it', () => {
        const bondingTrainer: IBondingTrainer = { id: 123 };
        const bondingTrainerCollection: IBondingTrainer[] = [
          {
            ...bondingTrainer,
          },
          { id: 456 },
        ];
        expectedResult = service.addBondingTrainerToCollectionIfMissing(bondingTrainerCollection, bondingTrainer);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BondingTrainer to an array that doesn't contain it", () => {
        const bondingTrainer: IBondingTrainer = { id: 123 };
        const bondingTrainerCollection: IBondingTrainer[] = [{ id: 456 }];
        expectedResult = service.addBondingTrainerToCollectionIfMissing(bondingTrainerCollection, bondingTrainer);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bondingTrainer);
      });

      it('should add only unique BondingTrainer to an array', () => {
        const bondingTrainerArray: IBondingTrainer[] = [{ id: 123 }, { id: 456 }, { id: 74763 }];
        const bondingTrainerCollection: IBondingTrainer[] = [{ id: 123 }];
        expectedResult = service.addBondingTrainerToCollectionIfMissing(bondingTrainerCollection, ...bondingTrainerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bondingTrainer: IBondingTrainer = { id: 123 };
        const bondingTrainer2: IBondingTrainer = { id: 456 };
        expectedResult = service.addBondingTrainerToCollectionIfMissing([], bondingTrainer, bondingTrainer2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bondingTrainer);
        expect(expectedResult).toContain(bondingTrainer2);
      });

      it('should accept null and undefined values', () => {
        const bondingTrainer: IBondingTrainer = { id: 123 };
        expectedResult = service.addBondingTrainerToCollectionIfMissing([], null, bondingTrainer, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bondingTrainer);
      });

      it('should return initial array if no BondingTrainer is added', () => {
        const bondingTrainerCollection: IBondingTrainer[] = [{ id: 123 }];
        expectedResult = service.addBondingTrainerToCollectionIfMissing(bondingTrainerCollection, undefined, null);
        expect(expectedResult).toEqual(bondingTrainerCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
