import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { State } from 'app/entities/enumerations/state.model';
import { IAreaTrainer, AreaTrainer } from '../area-trainer.model';

import { AreaTrainerService } from './area-trainer.service';

describe('AreaTrainer Service', () => {
  let service: AreaTrainerService;
  let httpMock: HttpTestingController;
  let elemDefault: IAreaTrainer;
  let expectedResult: IAreaTrainer | IAreaTrainer[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AreaTrainerService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      areaTrainerState: State.ACTIVE,
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

    it('should create a AreaTrainer', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new AreaTrainer()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AreaTrainer', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          areaTrainerState: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AreaTrainer', () => {
      const patchObject = Object.assign(
        {
          areaTrainerState: 'BBBBBB',
        },
        new AreaTrainer()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AreaTrainer', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          areaTrainerState: 'BBBBBB',
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

    it('should delete a AreaTrainer', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addAreaTrainerToCollectionIfMissing', () => {
      it('should add a AreaTrainer to an empty array', () => {
        const areaTrainer: IAreaTrainer = { id: 123 };
        expectedResult = service.addAreaTrainerToCollectionIfMissing([], areaTrainer);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(areaTrainer);
      });

      it('should not add a AreaTrainer to an array that contains it', () => {
        const areaTrainer: IAreaTrainer = { id: 123 };
        const areaTrainerCollection: IAreaTrainer[] = [
          {
            ...areaTrainer,
          },
          { id: 456 },
        ];
        expectedResult = service.addAreaTrainerToCollectionIfMissing(areaTrainerCollection, areaTrainer);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AreaTrainer to an array that doesn't contain it", () => {
        const areaTrainer: IAreaTrainer = { id: 123 };
        const areaTrainerCollection: IAreaTrainer[] = [{ id: 456 }];
        expectedResult = service.addAreaTrainerToCollectionIfMissing(areaTrainerCollection, areaTrainer);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(areaTrainer);
      });

      it('should add only unique AreaTrainer to an array', () => {
        const areaTrainerArray: IAreaTrainer[] = [{ id: 123 }, { id: 456 }, { id: 93381 }];
        const areaTrainerCollection: IAreaTrainer[] = [{ id: 123 }];
        expectedResult = service.addAreaTrainerToCollectionIfMissing(areaTrainerCollection, ...areaTrainerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const areaTrainer: IAreaTrainer = { id: 123 };
        const areaTrainer2: IAreaTrainer = { id: 456 };
        expectedResult = service.addAreaTrainerToCollectionIfMissing([], areaTrainer, areaTrainer2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(areaTrainer);
        expect(expectedResult).toContain(areaTrainer2);
      });

      it('should accept null and undefined values', () => {
        const areaTrainer: IAreaTrainer = { id: 123 };
        expectedResult = service.addAreaTrainerToCollectionIfMissing([], null, areaTrainer, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(areaTrainer);
      });

      it('should return initial array if no AreaTrainer is added', () => {
        const areaTrainerCollection: IAreaTrainer[] = [{ id: 123 }];
        expectedResult = service.addAreaTrainerToCollectionIfMissing(areaTrainerCollection, undefined, null);
        expect(expectedResult).toEqual(areaTrainerCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
