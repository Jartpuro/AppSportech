import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { Offer } from 'app/entities/enumerations/offer.model';
import { StateProgram } from 'app/entities/enumerations/state-program.model';
import { ITrainingProgram, TrainingProgram } from '../training-program.model';

import { TrainingProgramService } from './training-program.service';

describe('TrainingProgram Service', () => {
  let service: TrainingProgramService;
  let httpMock: HttpTestingController;
  let elemDefault: ITrainingProgram;
  let expectedResult: ITrainingProgram | ITrainingProgram[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TrainingProgramService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      programCode: 'AAAAAAA',
      programVersion: 'AAAAAAA',
      programName: Offer.ATHLETICS,
      programInitials: 'AAAAAAA',
      programState: StateProgram.EXECUTION,
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

    it('should create a TrainingProgram', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new TrainingProgram()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TrainingProgram', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          programCode: 'BBBBBB',
          programVersion: 'BBBBBB',
          programName: 'BBBBBB',
          programInitials: 'BBBBBB',
          programState: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TrainingProgram', () => {
      const patchObject = Object.assign(
        {
          programName: 'BBBBBB',
          programInitials: 'BBBBBB',
        },
        new TrainingProgram()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TrainingProgram', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          programCode: 'BBBBBB',
          programVersion: 'BBBBBB',
          programName: 'BBBBBB',
          programInitials: 'BBBBBB',
          programState: 'BBBBBB',
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

    it('should delete a TrainingProgram', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTrainingProgramToCollectionIfMissing', () => {
      it('should add a TrainingProgram to an empty array', () => {
        const trainingProgram: ITrainingProgram = { id: 123 };
        expectedResult = service.addTrainingProgramToCollectionIfMissing([], trainingProgram);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainingProgram);
      });

      it('should not add a TrainingProgram to an array that contains it', () => {
        const trainingProgram: ITrainingProgram = { id: 123 };
        const trainingProgramCollection: ITrainingProgram[] = [
          {
            ...trainingProgram,
          },
          { id: 456 },
        ];
        expectedResult = service.addTrainingProgramToCollectionIfMissing(trainingProgramCollection, trainingProgram);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TrainingProgram to an array that doesn't contain it", () => {
        const trainingProgram: ITrainingProgram = { id: 123 };
        const trainingProgramCollection: ITrainingProgram[] = [{ id: 456 }];
        expectedResult = service.addTrainingProgramToCollectionIfMissing(trainingProgramCollection, trainingProgram);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainingProgram);
      });

      it('should add only unique TrainingProgram to an array', () => {
        const trainingProgramArray: ITrainingProgram[] = [{ id: 123 }, { id: 456 }, { id: 39025 }];
        const trainingProgramCollection: ITrainingProgram[] = [{ id: 123 }];
        expectedResult = service.addTrainingProgramToCollectionIfMissing(trainingProgramCollection, ...trainingProgramArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const trainingProgram: ITrainingProgram = { id: 123 };
        const trainingProgram2: ITrainingProgram = { id: 456 };
        expectedResult = service.addTrainingProgramToCollectionIfMissing([], trainingProgram, trainingProgram2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainingProgram);
        expect(expectedResult).toContain(trainingProgram2);
      });

      it('should accept null and undefined values', () => {
        const trainingProgram: ITrainingProgram = { id: 123 };
        expectedResult = service.addTrainingProgramToCollectionIfMissing([], null, trainingProgram, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainingProgram);
      });

      it('should return initial array if no TrainingProgram is added', () => {
        const trainingProgramCollection: ITrainingProgram[] = [{ id: 123 }];
        expectedResult = service.addTrainingProgramToCollectionIfMissing(trainingProgramCollection, undefined, null);
        expect(expectedResult).toEqual(trainingProgramCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
