import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITrainee, Trainee } from '../trainee.model';

import { TraineeService } from './trainee.service';

describe('Trainee Service', () => {
  let service: TraineeService;
  let httpMock: HttpTestingController;
  let elemDefault: ITrainee;
  let expectedResult: ITrainee | ITrainee[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TraineeService);
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

    it('should create a Trainee', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Trainee()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Trainee', () => {
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

    it('should partial update a Trainee', () => {
      const patchObject = Object.assign({}, new Trainee());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Trainee', () => {
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

    it('should delete a Trainee', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTraineeToCollectionIfMissing', () => {
      it('should add a Trainee to an empty array', () => {
        const trainee: ITrainee = { id: 123 };
        expectedResult = service.addTraineeToCollectionIfMissing([], trainee);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainee);
      });

      it('should not add a Trainee to an array that contains it', () => {
        const trainee: ITrainee = { id: 123 };
        const traineeCollection: ITrainee[] = [
          {
            ...trainee,
          },
          { id: 456 },
        ];
        expectedResult = service.addTraineeToCollectionIfMissing(traineeCollection, trainee);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Trainee to an array that doesn't contain it", () => {
        const trainee: ITrainee = { id: 123 };
        const traineeCollection: ITrainee[] = [{ id: 456 }];
        expectedResult = service.addTraineeToCollectionIfMissing(traineeCollection, trainee);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainee);
      });

      it('should add only unique Trainee to an array', () => {
        const traineeArray: ITrainee[] = [{ id: 123 }, { id: 456 }, { id: 15812 }];
        const traineeCollection: ITrainee[] = [{ id: 123 }];
        expectedResult = service.addTraineeToCollectionIfMissing(traineeCollection, ...traineeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const trainee: ITrainee = { id: 123 };
        const trainee2: ITrainee = { id: 456 };
        expectedResult = service.addTraineeToCollectionIfMissing([], trainee, trainee2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainee);
        expect(expectedResult).toContain(trainee2);
      });

      it('should accept null and undefined values', () => {
        const trainee: ITrainee = { id: 123 };
        expectedResult = service.addTraineeToCollectionIfMissing([], null, trainee, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainee);
      });

      it('should return initial array if no Trainee is added', () => {
        const traineeCollection: ITrainee[] = [{ id: 123 }];
        expectedResult = service.addTraineeToCollectionIfMissing(traineeCollection, undefined, null);
        expect(expectedResult).toEqual(traineeCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
