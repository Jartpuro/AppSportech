import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { State } from 'app/entities/enumerations/state.model';
import { ITrainer, Trainer } from '../trainer.model';

import { TrainerService } from './trainer.service';

describe('Trainer Service', () => {
  let service: TrainerService;
  let httpMock: HttpTestingController;
  let elemDefault: ITrainer;
  let expectedResult: ITrainer | ITrainer[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TrainerService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      trainerState: State.ACTIVE,
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

    it('should create a Trainer', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Trainer()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Trainer', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          trainerState: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Trainer', () => {
      const patchObject = Object.assign(
        {
          trainerState: 'BBBBBB',
        },
        new Trainer()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Trainer', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          trainerState: 'BBBBBB',
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

    it('should delete a Trainer', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTrainerToCollectionIfMissing', () => {
      it('should add a Trainer to an empty array', () => {
        const trainer: ITrainer = { id: 123 };
        expectedResult = service.addTrainerToCollectionIfMissing([], trainer);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainer);
      });

      it('should not add a Trainer to an array that contains it', () => {
        const trainer: ITrainer = { id: 123 };
        const trainerCollection: ITrainer[] = [
          {
            ...trainer,
          },
          { id: 456 },
        ];
        expectedResult = service.addTrainerToCollectionIfMissing(trainerCollection, trainer);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Trainer to an array that doesn't contain it", () => {
        const trainer: ITrainer = { id: 123 };
        const trainerCollection: ITrainer[] = [{ id: 456 }];
        expectedResult = service.addTrainerToCollectionIfMissing(trainerCollection, trainer);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainer);
      });

      it('should add only unique Trainer to an array', () => {
        const trainerArray: ITrainer[] = [{ id: 123 }, { id: 456 }, { id: 78999 }];
        const trainerCollection: ITrainer[] = [{ id: 123 }];
        expectedResult = service.addTrainerToCollectionIfMissing(trainerCollection, ...trainerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const trainer: ITrainer = { id: 123 };
        const trainer2: ITrainer = { id: 456 };
        expectedResult = service.addTrainerToCollectionIfMissing([], trainer, trainer2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainer);
        expect(expectedResult).toContain(trainer2);
      });

      it('should accept null and undefined values', () => {
        const trainer: ITrainer = { id: 123 };
        expectedResult = service.addTrainerToCollectionIfMissing([], null, trainer, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainer);
      });

      it('should return initial array if no Trainer is added', () => {
        const trainerCollection: ITrainer[] = [{ id: 123 }];
        expectedResult = service.addTrainerToCollectionIfMissing(trainerCollection, undefined, null);
        expect(expectedResult).toEqual(trainerCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
