import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { State } from 'app/entities/enumerations/state.model';
import { ITrainingStatus, TrainingStatus } from '../training-status.model';

import { TrainingStatusService } from './training-status.service';

describe('TrainingStatus Service', () => {
  let service: TrainingStatusService;
  let httpMock: HttpTestingController;
  let elemDefault: ITrainingStatus;
  let expectedResult: ITrainingStatus | ITrainingStatus[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TrainingStatusService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      statusName: 'AAAAAAA',
      stateTraining: State.ACTIVE,
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

    it('should create a TrainingStatus', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new TrainingStatus()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TrainingStatus', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          statusName: 'BBBBBB',
          stateTraining: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TrainingStatus', () => {
      const patchObject = Object.assign({}, new TrainingStatus());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TrainingStatus', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          statusName: 'BBBBBB',
          stateTraining: 'BBBBBB',
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

    it('should delete a TrainingStatus', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTrainingStatusToCollectionIfMissing', () => {
      it('should add a TrainingStatus to an empty array', () => {
        const trainingStatus: ITrainingStatus = { id: 123 };
        expectedResult = service.addTrainingStatusToCollectionIfMissing([], trainingStatus);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainingStatus);
      });

      it('should not add a TrainingStatus to an array that contains it', () => {
        const trainingStatus: ITrainingStatus = { id: 123 };
        const trainingStatusCollection: ITrainingStatus[] = [
          {
            ...trainingStatus,
          },
          { id: 456 },
        ];
        expectedResult = service.addTrainingStatusToCollectionIfMissing(trainingStatusCollection, trainingStatus);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TrainingStatus to an array that doesn't contain it", () => {
        const trainingStatus: ITrainingStatus = { id: 123 };
        const trainingStatusCollection: ITrainingStatus[] = [{ id: 456 }];
        expectedResult = service.addTrainingStatusToCollectionIfMissing(trainingStatusCollection, trainingStatus);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainingStatus);
      });

      it('should add only unique TrainingStatus to an array', () => {
        const trainingStatusArray: ITrainingStatus[] = [{ id: 123 }, { id: 456 }, { id: 75042 }];
        const trainingStatusCollection: ITrainingStatus[] = [{ id: 123 }];
        expectedResult = service.addTrainingStatusToCollectionIfMissing(trainingStatusCollection, ...trainingStatusArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const trainingStatus: ITrainingStatus = { id: 123 };
        const trainingStatus2: ITrainingStatus = { id: 456 };
        expectedResult = service.addTrainingStatusToCollectionIfMissing([], trainingStatus, trainingStatus2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainingStatus);
        expect(expectedResult).toContain(trainingStatus2);
      });

      it('should accept null and undefined values', () => {
        const trainingStatus: ITrainingStatus = { id: 123 };
        expectedResult = service.addTrainingStatusToCollectionIfMissing([], null, trainingStatus, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainingStatus);
      });

      it('should return initial array if no TrainingStatus is added', () => {
        const trainingStatusCollection: ITrainingStatus[] = [{ id: 123 }];
        expectedResult = service.addTrainingStatusToCollectionIfMissing(trainingStatusCollection, undefined, null);
        expect(expectedResult).toEqual(trainingStatusCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
